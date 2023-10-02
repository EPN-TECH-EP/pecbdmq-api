package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_FORMACION;
import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.formacion.MateriaDocumentoDto;
import epntech.cbdmq.pe.servicio.MateriaParaleloDocumentoService;
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
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
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

    @Override
    public List<MateriaParaleloDocumento> getAll() {
        return repo.findAll();
    }

    @Override
    public List<MateriaDocumentoDto> getAllByCodMateriaParalelo(Integer codMateriaParalelo) {
        return repo.finDocumentoRutaByCodMateriaParalelo(codMateriaParalelo);
    }

    @Override
    public Optional<MateriaParaleloDocumento> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public MateriaParaleloDocumento saveConArchivo(MateriaParaleloDocumento obj, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException {
        return null;
    }

    @Override
    public MateriaParaleloDocumento save(MateriaParaleloDocumento obj) throws DataException {


        return repo.save(obj);
    }

    private String ruta(String codigo) {

        String resultado = null;
        PeriodoAcademico periodo = periodoAcademicoRepository.getPeriodoAcademicoActivo();

        resultado = ARCHIVOS_RUTA + PATH_PROCESO_FORMACION + periodo.getCodigo() + "/Materia/" + codigo + "/";

        return resultado;
    }

    @Override
    public List<DocumentoRuta> guardarArchivo(Integer materia, Boolean esTarea, List<MultipartFile> archivo)
            throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;

        resultado = ruta(materia.toString());
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
            documento = documentoRepository.save(documento);
            lista.add(documentos);
            MateriaParaleloDocumento matdoc = new MateriaParaleloDocumento();
            matdoc.setCodDocumento(documento.getCodDocumento());
            matdoc.setCodMateriaParalelo(materia);
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

}
