package epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.formacion.EstudianteMateriaDocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;
import epntech.cbdmq.pe.servicio.fichaPersonal.EstudianteMateriaDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteMateriaDocumentoServiceImpl implements EstudianteMateriaDocumentoService {

    @Autowired
    private EstudianteMateriaDocumentoRepository repo;
    @Autowired
    private DocumentoService documentoService;

    @Override
    public List<EstudianteMateriaDocumento> getAll() {
        return repo.findAll();
    }

    @Override
    public List<EstudianteMateriaDocumento> getAllByEstudianteAndMateriaParalelo(Integer codEstudiante, Integer codMateriaParalelo) {
        return repo.getEstudianteMateriaDocumentoByEstudianteMateriaParalelo(codEstudiante, codMateriaParalelo);
    }


    @Override
    public Optional<EstudianteMateriaDocumento> getEstudianteMateriaDocumentoById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public EstudianteMateriaDocumento saveOrUpdate(EstudianteMateriaDocumento estudianteMateriaDocumento) {
        return repo.save(estudianteMateriaDocumento);
    }

    @Override
    public EstudianteMateriaDocumento saveConArchivo(EstudianteMateriaDocumento estudianteMateriaDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException {
        List<Integer> idsDocumento = documentoService.guardarArchivoCompleto("FORMACION", estudianteMateriaDocumento.getCodEstudianteMateriaParalelo().toString(), archivos);
        if (idsDocumento.size() > 0) {
            for (Integer idDocumento : idsDocumento) {
                estudianteMateriaDocumento.setCodDocumento(idDocumento);
                repo.save(estudianteMateriaDocumento);
            }
        }
        return estudianteMateriaDocumento;
    }

    @Override
    public void deleteEstudianteMateriaDocumento(Integer id) {
        repo.deleteById(id);
    }
}
