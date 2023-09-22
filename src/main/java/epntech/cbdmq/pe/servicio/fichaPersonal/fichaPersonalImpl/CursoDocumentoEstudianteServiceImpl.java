package epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.CursoDocumentoEstudiante;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoDocumentoEstudianteRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;
import epntech.cbdmq.pe.servicio.fichaPersonal.CursoDocumentoEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;

@Service
public class CursoDocumentoEstudianteServiceImpl implements CursoDocumentoEstudianteService {
    @Autowired
    private CursoDocumentoEstudianteRepository repo;
    @Autowired
    private DocumentoService documentoService;

    @Override
    public List<CursoDocumentoEstudiante> getAll() {
        return null;
    }

    @Override
    public List<CursoDocumentoEstudiante> getAllByEstudianteAndCurso(Integer codEstudiante, Integer codMateriaParalelo) {
        return repo.getCursoDocumentoEstudianteByEstudianteCurso(codEstudiante, codMateriaParalelo);
    }

    @Override
    public Optional<CursoDocumentoEstudiante> getEstudianteCursoDocumentoById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public CursoDocumentoEstudiante saveOrUpdate(CursoDocumentoEstudiante estudianteMateriaDocumento) {
        return repo.save(estudianteMateriaDocumento);
    }

    @Override
    public CursoDocumentoEstudiante saveConArchivo(CursoDocumentoEstudiante estudianteCursoDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException {
        List<Integer> idsDocumento = documentoService.guardarArchivoCompleto("ESPECIALIZACION", estudianteCursoDocumento.getCodEstudiante().toString(), archivos);
        CursoDocumentoEstudiante curso = null;
        if (idsDocumento.size() > 0) {
            for (Integer idDocumento : idsDocumento) {
                estudianteCursoDocumento.setCodDocumento(idDocumento);
                estudianteCursoDocumento.setEstado(ACTIVO);
                curso = repo.save(estudianteCursoDocumento);
            }
        }
        return curso;
    }

    @Override
    public void deleteEstudianteMateriaDocumento(Integer id) {
        CursoDocumentoEstudiante estudianteMateriaDocumento = repo.findById(id).get();
        if (estudianteMateriaDocumento.getCodDocumento() != null) {
            repo.deleteById(id);
            documentoService.delete(estudianteMateriaDocumento.getCodDocumento());
        }

    }
}
