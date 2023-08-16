package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionDocumento;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionGeneral;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProDocumentoForRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionDocumentoRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionGeneralRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProNotaProfesionalizacionGeneralService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;

@Service
public class ProNotaProfesionalizacionGeneralServiceImpl extends ProfesionalizacionServiceImpl<ProNotaProfesionalizacionGeneral, Integer, ProNotaProfesionalizacionGeneralRepository> implements ProNotaProfesionalizacionGeneralService {
    private final ProDocumentoForRepository proDocumentoForRepository;
    private final ProNotaProfesionalizacionDocumentoRepository documentoRepository;

    public ProNotaProfesionalizacionGeneralServiceImpl(ProNotaProfesionalizacionGeneralRepository repository, ProDocumentoForRepository proDocumentoForRepository, ProNotaProfesionalizacionDocumentoRepository documentoRepository) {
        super(repository);
        this.proDocumentoForRepository = proDocumentoForRepository;
        this.documentoRepository = documentoRepository;
    }

    public ProNotaProfesionalizacionGeneral insertarNotasConDocumentos(ProNotaProfesionalizacionGeneral profesionalizacionGeneral, List<MultipartFile> docsNota) throws DataException {
        profesionalizacionGeneral.setEstado("ACTIVO");
        Optional<ProNotaProfesionalizacionGeneral> general = repository.findByCodMateriaParalelo(profesionalizacionGeneral.getCodMateriaParalelo());
        ProNotaProfesionalizacionGeneral notaProfesionalizacionGeneral;
        if (general.isPresent()) {
            notaProfesionalizacionGeneral = general.get();
            notaProfesionalizacionGeneral.setFecha(profesionalizacionGeneral.getFecha());
        } else {
            notaProfesionalizacionGeneral = profesionalizacionGeneral;
        }
        notaProfesionalizacionGeneral = repository.save(notaProfesionalizacionGeneral);

        List<DatosFile> archivosConvocatoria = new ArrayList<>();
        try {
            archivosConvocatoria = guardarArchivo(docsNota, PATH_PROFESIONALIZACION + PATH_PROCESO_NOTAS_PROFESIONALIZACION_FOR, profesionalizacionGeneral.getCodMateriaParalelo().toString());
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorHeader", e.getMessage());
        }

        Set<DocumentoFor> dPeriodoAcademico = new HashSet<>();

        for (DatosFile datosFile : archivosConvocatoria) {
            DocumentoFor dd1 = new DocumentoFor();
            dd1.setEstado("ACTIVO");
            dd1.setNombre(datosFile.getNombre());
            dd1.setRuta(datosFile.getRuta());
            dPeriodoAcademico.add(dd1);
        }
        proDocumentoForRepository.saveAll(dPeriodoAcademico);

        Set<ProNotaProfesionalizacionDocumento> dPeriodoAcademicoSet = new HashSet<>();
        for (DocumentoFor elemento : dPeriodoAcademico) {
            ProNotaProfesionalizacionDocumento proInscripcionDocumento = new ProNotaProfesionalizacionDocumento();
            proInscripcionDocumento.setCodNotaProfesionalizacion(notaProfesionalizacionGeneral.getCodigo());
            proInscripcionDocumento.setCodDocumento(elemento.getCodDocumento());
            dPeriodoAcademicoSet.add(proInscripcionDocumento);
        }
        documentoRepository.saveAll(dPeriodoAcademicoSet);
        return notaProfesionalizacionGeneral;
    }

    public Optional<ProNotaProfesionalizacionGeneral> getByMateriaparalelo(Integer id) {
        return repository.findByCodMateriaParalelo(id);
    }
}
