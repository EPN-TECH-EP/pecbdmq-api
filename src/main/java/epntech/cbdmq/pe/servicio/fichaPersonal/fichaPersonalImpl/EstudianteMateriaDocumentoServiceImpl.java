package epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.EstudianteMateriaDocumentoDto;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.formacion.EstudianteMateriaDocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;
import epntech.cbdmq.pe.servicio.fichaPersonal.EstudianteMateriaDocumentoService;
import epntech.cbdmq.pe.servicio.formacion.EstudianteMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;

@Service
public class EstudianteMateriaDocumentoServiceImpl implements EstudianteMateriaDocumentoService {

    @Autowired
    private EstudianteMateriaDocumentoRepository repo;
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private EstudianteMateriaParaleloService estudianteMateriaParaleloService;

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
    public EstudianteMateriaDocumento saveConArchivo(EstudianteMateriaDocumentoDto estudianteMateriaDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException {
        List<Integer> idsDocumento = documentoService.guardarArchivoCompleto("FORMACION", estudianteMateriaDocumento.getCodEstudiante().toString(), archivos);
        EstudianteMateriaDocumento estudianteMateriaDocumento1 = new EstudianteMateriaDocumento();
        EstudianteMateriaParalelo estudianteMateriaParalelo = estudianteMateriaParaleloService.findByCodEstudianteAndCodMateriaParalelo(estudianteMateriaDocumento.getCodEstudiante(), estudianteMateriaDocumento.getCodMateriaParalelo()).get();
        if (estudianteMateriaParalelo == null) {
            return null;
        }
        estudianteMateriaDocumento1.setCodEstudianteMateriaParalelo(estudianteMateriaParalelo.getCodEstudianteMateriaParalelo());
        if (idsDocumento.size() > 0) {
            for (Integer idDocumento : idsDocumento) {
                estudianteMateriaDocumento1.setCodDocumento(idDocumento);
                estudianteMateriaDocumento1.setEstado(ACTIVO);
                estudianteMateriaDocumento1.setDescripcion(estudianteMateriaDocumento.getDescripcion());
                repo.save(estudianteMateriaDocumento1);
            }
        }
        return estudianteMateriaDocumento1;
    }

    @Override
    public void deleteEstudianteMateriaDocumento(Integer id) {
        EstudianteMateriaDocumento estudianteMateriaDocumento = repo.findById(id).get();
        if (estudianteMateriaDocumento.getCodDocumento() != null) {
            repo.deleteById(id);
            documentoService.delete(estudianteMateriaDocumento.getCodDocumento());
        }


    }
}
