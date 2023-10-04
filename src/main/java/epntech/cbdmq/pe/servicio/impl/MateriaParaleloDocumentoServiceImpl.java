package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_FORMACION;
import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.DOCUMENTO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.servicio.MateriaParaleloDocumentoService;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaParaleloDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;

@Service
public class MateriaParaleloDocumentoServiceImpl implements MateriaParaleloDocumentoService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;

    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MÁXIMO;
    @Autowired
    MateriaParaleloDocumentoRepository repo;

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    PeriodoAcademicoRepository periodoAcademicoRepository;
    @Autowired
    MateriaParaleloService materiaParaleloService;

    @Override
    public List<MateriaParaleloDocumento> getAll() {
        return repo.findAll();
    }

    @Override
    public List<MateriaCursoDocumentoDto> getAllByCodMateriaParalelo(Integer codMateriaParalelo, Integer idParalelo) {
        return repo.finDocumentoRutaByCodMateriaParalelo(codMateriaParalelo,idParalelo);
    }

    @Override
    public Optional<MateriaParaleloDocumento> findById(Integer id) {
        return repo.findById(id);
    }


    @Override
    public MateriaParaleloDocumento save(MateriaParaleloDocumento obj) throws DataException {

        return repo.save(obj);
    }

    private String ruta(String codigo) {

        String resultado;
        PeriodoAcademico periodo = periodoAcademicoRepository.getPeriodoAcademicoActivo();

        resultado = ARCHIVOS_RUTA + PATH_PROCESO_FORMACION + periodo.getCodigo() + "/Materia/" + codigo + "/";

        return resultado;
    }

    @Override
    public List<DocumentoRuta> guardarArchivo(Integer materiaPeriodo, Integer paralelo,  Boolean esTarea, List<MultipartFile> archivo, String descripcion)
            throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;
        Integer codMateriaParalelo= materiaParaleloService.findByCodMateriaPeriodoAndCodParalelo(materiaPeriodo,paralelo).get().getCodMateriaParalelo();


        resultado = ruta(codMateriaParalelo.toString());
        Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        List<DocumentoRuta> lista = new ArrayList<>();
        DocumentoRuta documentos = new DocumentoRuta();
        for (Iterator iterator = archivo.iterator(); iterator.hasNext(); ) {
            MultipartFile multipartFile = (MultipartFile) iterator.next();
            if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Archivo guardado: " + resultado + multipartFile.getOriginalFilename());
            documentos.setNombre(multipartFile.getOriginalFilename());
            documentos.setRuta(resultado + multipartFile.getOriginalFilename());
            Documento documento = new Documento();
            documento.setEstado(ACTIVO);
            documento.setNombre(multipartFile.getOriginalFilename());
            documento.setRuta(resultado + multipartFile.getOriginalFilename());
            documento.setDescripcion(descripcion);
            documento = documentoRepository.save(documento);
            lista.add(documentos);
            MateriaParaleloDocumento matdoc = new MateriaParaleloDocumento();
            matdoc.setCodDocumento(documento.getCodDocumento());
            matdoc.setCodMateriaParalelo(codMateriaParalelo);
            matdoc.setEsTarea(esTarea);
            repo.save(matdoc);

        }
        return lista;
    }

    @Override
    public void deleteDocumento(Integer codMateriaParaleloDocumento) throws DataException {
        MateriaParaleloDocumento materiaParaleloDocumento = repo.findById(codMateriaParaleloDocumento).get();
        if (materiaParaleloDocumento.getCodDocumento() != null) {
            Optional<Documento> documentoOptional;
            Documento documento;

            documentoOptional = documentoRepository.findById((materiaParaleloDocumento.getCodDocumento()));
            documento = documentoOptional.get();

            Path ruta = Paths.get(documento.getRuta());

            if (Files.exists(ruta)) {
                try {
                    Files.delete(ruta);
                    repo.deleteById(codMateriaParaleloDocumento);
                    documentoRepository.deleteById(materiaParaleloDocumento.getCodDocumento());

                } catch (Exception e) {

                    throw new DataException(e.getMessage());
                }

            }
        }

    }

    @Override
    public Set<Documento> getDocumentosByMateriaParalelo(Long codMateriaParalelo) throws IOException {
        return documentoRepository.getDocumentosMateriaFormacion(codMateriaParalelo.intValue());
    }

    @Override
    public Set<Documento> getTareasByMateriaParalelo(Long codMateriaParalelo) throws IOException {
        return documentoRepository.getTareasMateriaFormacion(codMateriaParalelo.intValue(), true);
    }

    @Override
    public void deleteDocumentoI(Long codMateriaParalelo, Long codDocumento) throws DataException, IOException {
        MateriaParalelo materiaParalelo = materiaParaleloService.getById(Integer.valueOf(codMateriaParalelo.intValue())).get();
        if (materiaParalelo == null)
            new BusinessException(REGISTRO_NO_EXISTE);

        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));


        Path ruta;

        ruta = Paths.get(documento.getRuta()).toAbsolutePath().normalize();

        try {
            Files.delete(ruta);
            repo.deleteByCodMateriaParaleloAndCodDocumento(materiaParalelo.getCodMateriaParalelo(), codDocumento.intValue());
            documentoRepository.deleteById(codDocumento.intValue());

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }


}
