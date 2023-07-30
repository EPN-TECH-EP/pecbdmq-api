package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CURSO_RECHAZO_DOCUMENTO;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.EspecializacionConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.dominio.admin.especializacion.*;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.*;
import epntech.cbdmq.pe.servicio.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private CursoEspRepository cursoEspRepository;
	@Autowired
	private CursoDocumentoRepository cursoDocumentoRepository;
	@Autowired
	private CursoRequisitoRepository cursoRequisitoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private AulaRepository aulaRepository;
	@Autowired
	private CursoInstructorRepository cursoInstructorRepository;
	@Autowired
	private ParametroRepository parametroRepository;
	@Autowired
	private TipoCursoRepository tipoCursoRepository;
	@Autowired
	private CatalogoCursoRepository catalogoCursoRepository;

	@Autowired
	private EmailService emailService;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MAXIMO;

	@Override
	public Curso save(String datos, List<MultipartFile> documentos, Long codTipoDocumento) throws JsonProcessingException, ParseException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		JsonNode jsonNode = objectMapper.readTree(datos);
		System.out.println("jsonNode: " + jsonNode);

		Curso cursoDatos = objectMapper.readValue(datos, Curso.class);


		Set<Requisito> requisitos = cursoDatos.getRequisitos();

		Set<Requisito> reqs = new HashSet<>();
		for (Requisito r : requisitos) {
			Requisito requisito = new Requisito();
			requisito.setCodigoRequisito(r.getCodigoRequisito());
			reqs.add(requisito);
		}

		Aula aula = aulaRepository.findById(cursoDatos.getCodAula())
				.orElseThrow(() -> new BusinessException(AULA_NO_EXISTE));

		cursoDatos.setCodAula(aula.getCodAula());
		Curso cc;
		cc = cursoEspRepository.insertarCursosDocumentosRequisitos(cursoDatos, requisitos, documentos, codTipoDocumento);

		return cc;
	}

	@Override
	public Curso update(Curso objActualizado) {
		Curso curso = cursoRepository.findById(objActualizado.getCodCursoEspecializacion())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		curso.setCodAula(objActualizado.getCodAula());
		curso.setNumeroCupo(objActualizado.getNumeroCupo());
		curso.setFechaInicioCargaNota(objActualizado.getFechaInicioCargaNota());
		curso.setFechaFinCargaNota(objActualizado.getFechaFinCargaNota());
		curso.setNotaMinima(objActualizado.getNotaMinima());
		curso.setCodCatalogoCursos(objActualizado.getCodCatalogoCursos());
		curso.setEmailNotificacion(objActualizado.getEmailNotificacion());
		curso.setEstado(objActualizado.getEstado());
		curso.setTieneModulos(objActualizado.getTieneModulos());

		return cursoRepository.save(objActualizado);
	}

	@Override
	public List<Curso> listarAll() {
		return cursoRepository.findAll();
	}

	@Override
	public List<Curso> listarPorEstado(String estado) {
		return cursoRepository.findByEstado(estado);
	}

	@Override
	public List<Curso> getByCodigoTipoCurso(Integer codigoTipoCurso) {
		TipoCurso tipoCurso = tipoCursoRepository.findById(codigoTipoCurso.longValue())
				.orElseThrow(() -> new BusinessException(TIPO_CURSO_NO_EXISTE));
		return cursoRepository.findByCodigoTipoCurso(tipoCurso.getCodTipoCurso().intValue());
	}

	@Override
	public List<Curso> getByCodigoCatalogoCurso(Integer codigoCatalogoCurso) {
		CatalogoCurso catalogoCurso = catalogoCursoRepository.findById(codigoCatalogoCurso)
				.orElseThrow(() -> new BusinessException(CATALOGO_CURSO_NO_EXISTE));
		return cursoRepository.findByCodCatalogoCursos(catalogoCurso.getCodCatalogoCursos().longValue());
	}

	@Override
	public Curso getById(Long id) {
		return cursoRepository.findById(id)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public CursoDocumento updateEstadoAprobadoValidado(Boolean estadoAprobado, Boolean estadoValidado,
													   String observaciones, Long codCursoEspecializacion, Long codDocumento) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		Documento documento = documentoRepository.findById(codDocumento.intValue())
				.orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

		cursoDocumentoRepository.updateEstadoAprobado(estadoAprobado, estadoValidado, observaciones,
				codCursoEspecializacion, codDocumento);
		cursoRepository.validaDocumentosCursoEspecializacion(codCursoEspecializacion);

		if (Boolean.TRUE.equals(!estadoAprobado) || Boolean.TRUE.equals(!estadoValidado))
			notificarRechazo(curso.getCodCursoEspecializacion(), documento, observaciones);

		return cursoDocumentoRepository.findByCodCursoEspecializacionAndCodDocumento(codCursoEspecializacion,
				codDocumento);
	}

	private void notificarRechazo(Long codCursoEspecializacion, Documento documento, String observaciones) {
		cursoInstructorRepository.findInstructoresCurso(codCursoEspecializacion)
				.forEach(ci -> {
					notificarRechazoInstructor(ci, documento, observaciones);
				});
	}

	private void notificarRechazoInstructor(InstructoresCurso instructoresCurso, Documento documento, String observaciones) {
		Parametro parametro = parametroRepository.findByNombreParametro("especializacion.rechazo.curso.notificacion.body")
				.orElseThrow(() -> new BusinessException(NO_PARAMETRO));
		String nombres = instructoresCurso.getNombre() + " " + instructoresCurso.getApellido();
		String cuerpoHtml = String.format(parametro.getValor(), nombres, instructoresCurso.getNombreCatalogoCurso(), documento.getNombre(), observaciones);
		String[] destinatarios = {instructoresCurso.getCorreoInstitucional(), instructoresCurso.getCorreoPersonal()};
		emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_CURSO_RECHAZO_DOCUMENTO, cuerpoHtml);
	}

	@Override
	public Curso iniciarCurso(Long codCursoEspecializacion) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		if (Boolean.FALSE.equals(curso.getApruebaCreacionCurso())) {
			throw new BusinessException(CURSO_NO_VALIDADO);
		}

		curso.setEstado(CURSO_ESTADO_INICIADO);
		cursoRepository.save(curso);

		return curso;
	}

	@Override
	public Curso updateEstadoProceso(Long estado, Long codCurso) {
		Curso curso = cursoRepository.findById(codCurso)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		int result = cursoRepository.updateEstadoProceso(estado, curso.getCodCursoEspecializacion());
		if (result == 1)
			return cursoRepository.findById(curso.getCodCursoEspecializacion()).get();
		else
			throw new BusinessException(ESTADO_INCORRECTO);
	}

	@Override
	public Curso updateRequisitos(Long codCursoEspecializacion, List<Requisito> requisitos) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		Optional<CursoRequisito> cursoRequisito = cursoRequisitoRepository
				.findFirstByCodCursoEspecializacion(curso.getCodCursoEspecializacion());
		if (cursoRequisito.isPresent())
			cursoRequisitoRepository.deleteByCodCursoEspecializacion(codCursoEspecializacion);

		for (Requisito requisito : requisitos) {
			CursoRequisito cr = new CursoRequisito();
			cr.setCodCursoEspecializacion(codCursoEspecializacion);
			cr.setCodRequisito((long) requisito.getCodigoRequisito());
			cursoRequisitoRepository.save(cr);
		}

		return curso;
	}

	@Override
	public Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException {
		Documento documento = documentoRepository.findById(codDocumento.intValue())
				.orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

		if(archivo.getSize() == 0)
			throw new BusinessException(NO_ADJUNTO);

		Path ruta = Paths.get(documento.getRuta());

		try {

			if (Files.exists(ruta)) {
				Files.delete(ruta);
			}

			if (archivo.getSize() > TAMAÑO_MAXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}
			if (!Files.exists(ruta)) {
				Files.createDirectories(ruta);
			}
			Files.copy(archivo.getInputStream(), ruta.getParent().resolve(Objects.requireNonNull(archivo.getOriginalFilename())),
					StandardCopyOption.REPLACE_EXISTING);

			documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(ruta.getParent() + "/" + archivo.getOriginalFilename());
			documentoRepository.save(documento);

		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		return documento;
	}

	@Override
	public Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos, Long codTipoDocumento) throws IOException, ArchivoMuyGrandeExcepcion {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
		guardarDocumentos(archivos, curso.getCodCursoEspecializacion(), codTipoDocumento);
		return curso;
	}

	public void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion, Long tipoDocumento)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(codCursoEspecializacion);
		Path ruta = Paths.get(resultado);

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

        for (MultipartFile multipartFile : archivos) {
            if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            // LOGGER.info("Archivo guardado: " + resultado +
            Documento documento = new Documento();
            documento.setEstado("ACTIVO");
            documento.setTipo(tipoDocumento.intValue());
            documento.setNombre(multipartFile.getOriginalFilename());
            documento.setRuta(resultado + multipartFile.getOriginalFilename());
            documento = documentoRepository.save(documento);

            CursoDocumento cursoDocumento = new CursoDocumento();
            cursoDocumento.setCodCursoEspecializacion(codCursoEspecializacion);
            cursoDocumento.setCodDocumento((long) documento.getCodDocumento());
            cursoDocumentoRepository.save(cursoDocumento);
        }

	}

	private String ruta(Long codigo) {
		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codigo + "/";
		return resultado;
	}

	@Override
	public void delete(Long codCursoEspecializacion) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		cursoRepository.deleteById(curso.getCodCursoEspecializacion());

	}

	@Override
	public Boolean cumpleMinimoAprobadosCurso(Long codCursoEspecializacion) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		return cursoRepository.cumplePorcentajeMinimoAprobadosCurso(curso.getCodCursoEspecializacion());
	}

	@Override
	public void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		Documento documento = documentoRepository.findById(codDocumento.intValue())
				.orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

		Path ruta = Paths.get(documento.getRuta());

		if (Files.exists(ruta)) {
			try {
				Files.delete(ruta);
				cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(curso.getCodCursoEspecializacion(), codDocumento);
				documentoRepository.deleteById(codDocumento.intValue());
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}

		}

	}

}
