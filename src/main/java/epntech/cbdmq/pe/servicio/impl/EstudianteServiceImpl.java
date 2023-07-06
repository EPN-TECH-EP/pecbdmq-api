package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.servicio.PostulanteDatosService;
import epntech.cbdmq.pe.servicio.PostulantesValidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.repositorio.admin.AspirantesRepository;
import epntech.cbdmq.pe.repositorio.admin.EstudianteForRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.servicio.EstudianteService;

import java.util.stream.Collectors;


@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository repo;
    @Autowired
    private EstudianteForRepository estudianteForRepository;
    @Autowired
    private AspirantesRepository aspirantesRepository;
    @Autowired
    private PostulantesValidosService postulantesValidosService;


    @Override
    public Estudiante save(Estudiante obj) {
        // TODO Auto-generated method stub
        return repo.save(obj);
    }

    @Override
    public List<Estudiante> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    @Override
    public Optional<Estudiante> getById(int id) {
        // TODO Auto-generated method stub
        return repo.findById(id);
    }

    @Override
    public Estudiante update(Estudiante objActualizado) {
        // TODO Auto-generated method stub
        return repo.save(objActualizado);
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        repo.deleteById(id);
    }

    @Override
    public Optional<Estudiante> getByIdEstudiante(String id) {
        // TODO Auto-generated method stub
        return repo.findByCodUnicoEstudiante(id);
    }

	/*@Override
	public Page<EstudianteDatos> getAllEstudiante(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return repo.findAllEstudiante(pageable);
	}

	@Override
	public List<EstudianteDatos> findAllEstudiante() {
		// TODO Auto-generated method stub
		return this.repo.findAllEstudiante();
	}*/

    @Override
    public void saveEstudiantes() {
        // TODO Auto-generated method stub
        estudianteForRepository.insertEstudiantes();
    }

    @Override
    public Estudiante getEstudianteByUsuario(String codUsuario) {

        return this.repo.getEstudianteByUsuario(codUsuario);
    }

    @Override
    public String getCedulaByEstudiante(Integer codEstudiante) {
        return repo.getCedulaByEstudiante(codEstudiante);
    }

    @Override
    public List<Estudiante> getEstudiantesPA() {
        List<Estudiante> estudiantes = this.getAll();
        List<PostulantesValidos> postulantes = postulantesValidosService.getPostulantesValidos();
        List<Estudiante> estudiantesFiltrados = estudiantes.stream()
                .filter(estudiante -> postulantes.stream()
                        .anyMatch(postulante -> postulante.getCedula().equals(
                                this.getCedulaByEstudiante(estudiante.getCodEstudiante())
                        )))
                .collect(Collectors.toList());

        return estudiantesFiltrados;
    }

}
