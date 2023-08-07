package epntech.cbdmq.pe.servicio.impl.especializacion;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoDocumentoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoDocumentoService;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.EspecializacionConst.CURSO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.DOCUMENTO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
public class CursoDocumentoServiceImpl implements CursoDocumentoService {
    @Autowired
    private CursoService cursoRepository;
    @Autowired
    private CursoDocumentoRepository cursoDocumentoRepository;
    @Autowired
    private DocumentoRepository documentoRepository;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;

    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MÁXIMO;


    @Override
    public Set<Documento> getDocumentos(Long codCurso) throws IOException {
        return documentoRepository.getDocumentosEspecializacion(codCurso.intValue());
    }

    @Override
    public Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException {
        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

        if (archivo.getSize() == 0)
            throw new BusinessException(NO_ADJUNTO);

        Path ruta = Paths.get(documento.getRuta());

        try {

            if (Files.exists(ruta)) {
                Files.delete(ruta);
            }

            if (archivo.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
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
    public Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException {
        Curso curso = cursoRepository.getById(codCursoEspecializacion);
        if (curso == null)
            new BusinessException(REGISTRO_NO_EXISTE);
        guardarDocumentos(archivos, curso.getCodCursoEspecializacion());
        return curso;
    }

    @Override
    public void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) {
        Curso curso = cursoRepository.getById(codCursoEspecializacion);
        if (curso == null)
            new BusinessException(REGISTRO_NO_EXISTE);

        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

        Path ruta = Paths.get(documento.getRuta());

        if (Files.exists(ruta)) {
            try {
                Files.delete(ruta);
                cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(curso.getCodCursoEspecializacion(), codDocumento);
                documentoRepository.deleteById(codDocumento.intValue());
            } catch (Exception e) {
                LOGGER.error("No se puede eliminar el archivo: " + ruta + ". ERROR=" + e.getMessage());
                throw new BusinessException(e.getMessage());

            }

        }

    }

    public void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion)
            throws IOException, ArchivoMuyGrandeExcepcion, DataException {
        String resultadoRuta;

        resultadoRuta = ruta(codCursoEspecializacion);
        Path ruta = Paths.get(resultadoRuta);

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        for (MultipartFile multipartFile : archivos) {
            if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            // LOGGER.info("Archivo guardado: " + resultado +
            this.generaDocumento(resultadoRuta, multipartFile.getOriginalFilename(), codCursoEspecializacion);
        }

    }

    @Override
    @Transactional
    public void generaDocumento(String ruta, String nombre, Long codCursoEspecializacion) throws DataException {

        // busca la pruebaDetalle. Si no encuentra hay un error de consistencia de datos
        Long codCursoEspecial = codCursoEspecializacion;


        Curso curso = cursoRepository.getById(codCursoEspecial);
        if (curso != null) {
            codCursoEspecial = curso.getCodCursoEspecializacion();
        } else {
            throw new DataException(CURSO_NO_EXISTE);
        }

        // busca documentos para el curso
        List<CursoDocumento> listaDocCurso = cursoDocumentoRepository.findAllByCodCursoEspecializacion(codCursoEspecial);

        if (listaDocCurso != null && listaDocCurso.size() > 0) {


            // busca si existe un documento con el mismo nombre
            List<Documento> docsGeneral = this.documentoRepository.findAllByNombre(nombre);

            // si hay documentos con el mismo nombre, busca el que corresponda a ese curso
            if (docsGeneral != null && docsGeneral.size() > 0) {

                List<Integer> listaCodCurso = listaDocCurso.stream()
                        .map(p -> {
                            return p.getCodDocumento().intValue();
                        })
                        .collect(Collectors.toList());

                List<Integer> listaCodDocumentoGeneral = docsGeneral.stream()
                        .map(Documento::getCodDocumento)
                        .collect(Collectors.toList());

                // intersección de las listas
                Set<Integer> resultado = listaCodCurso.stream()
                        .distinct()
                        .filter(listaCodDocumentoGeneral::contains)
                        .collect(Collectors.toSet());

                if (resultado != null && resultado.size() > 0) {

                    final Long cursoEspecializacion = codCursoEspecial;

                    resultado.stream()
                            .forEach(codDoc -> {
                                // elimina de documentoPrueba
                                try {
                                    cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(cursoEspecializacion, codDoc.longValue());
                                    documentoRepository.deleteById(codDoc);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            });

                }

            }

        }

        // genera documento
        Documento documento = new Documento();
        documento.setEstado("ACTIVO");
        documento.setNombre(nombre);
        documento.setRuta(ruta);


        documento = documentoRepository.save(documento);

        CursoDocumento cursoActivoDocumento = new CursoDocumento();
        cursoActivoDocumento.setCodCursoEspecializacion(codCursoEspecial);
        cursoActivoDocumento.setCodDocumento(documento.getCodDocumento().longValue());
        cursoDocumentoRepository.save(cursoActivoDocumento);

    }

    private String ruta(Long codigo) {
        String resultado = null;
        resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codigo + "/";
        return resultado;
    }
}
