package epntech.cbdmq.pe.servicio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.DatosEstudianteParaCrearUsuario;
import epntech.cbdmq.pe.dominio.util.EstudianteDto;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.DatoPersonalService;
import epntech.cbdmq.pe.servicio.PostulantesValidosService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.repositorio.admin.AspirantesRepository;
import epntech.cbdmq.pe.repositorio.admin.EstudianteForRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.servicio.EstudianteService;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private EstudianteRepository repo;
    @Autowired
    private EstudianteForRepository estudianteForRepository;
    @Autowired
    private AspirantesRepository aspirantesRepository;
    @Autowired
    private PostulantesValidosService postulantesValidosService;
    @Autowired
    private DatoPersonalService dpService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;




    @Override
    public Estudiante save(Estudiante obj) {
        // TODO Auto-generated method stub
        return repo.save(obj);
    }

    @Override
    public List<Estudiante> getAll() {
        return repo.findAll();
    }

    @Override
    public List<Estudiante> getAllWithOutParalelo() {
        return repo.estudiantesWithParalelo();
    }

    @Override
    public Optional<Estudiante> getById(int id) {
        return repo.findById(id);
    }

    @Override
    public Estudiante update(Estudiante objActualizado) {
        return repo.save(objActualizado);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Estudiante> getByIdEstudiante(String id) {
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
    public void saveEstudiantes() throws DataException {


        try {
            String sqlInsertUsuario = "INSERT INTO cbdmq.gen_usuario \n" +
                    "(clave, cod_modulo, fecha_registro, fecha_ultimo_login, fecha_ultimo_login_mostrar, is_active, is_not_locked, nombre_usuario, cod_datos_personales)\n" +
                    "VALUES(:clave, 0, current_date, null, null, true, true, :usuario, :cod_dato_personal) returning cod_usuario;";

            String sqlInsertRolUsuario = "insert into cbdmq.gen_rol_usuario (cod_rol, cod_usuario) \n" +
                    "values ((select cod_rol from cbdmq.gen_rol gr where upper(trim(nombre)) like 'ESTUDIANTE'), :cod_usuario) \n" +
                    "ON CONFLICT (cod_rol, cod_usuario) do nothing;";

            estudianteForRepository.insertEstudiantes();

            // generar usuarios para los estudiantes registrados
            Set<DatosEstudianteParaCrearUsuario> listaEstudiantesParaCrearUsuarios = estudianteForRepository
                    .listaEstudiantesParaCrearUsuarios();


            for (DatosEstudianteParaCrearUsuario estudiante : listaEstudiantesParaCrearUsuarios) {



                try {
                    // insert en gen_usuario
                    Query insertUsuario =
                            entityManager.createNativeQuery(sqlInsertUsuario)
                            .setParameter("clave", this.encodePassword(estudiante.getCedula()))
                            .setParameter("usuario", estudiante.getCedula())
                            .setParameter("cod_dato_personal", estudiante.getCodDatosPersonales());

                    Long codUsuario = (Long) insertUsuario.getSingleResult();

                    Query insertRolUsuario =
                            entityManager.createNativeQuery(sqlInsertRolUsuario)
                            .setParameter("cod_usuario", codUsuario);

                    insertRolUsuario.executeUpdate();

                } catch (Exception e) {
                    LOGGER.error("Error al crear usuario para el estudiante: " + estudiante.getCedula()
                            + "clave:" + this.encodePassword(estudiante.getCedula())
                            + "cod_dato_personal: " + estudiante.getCodDatosPersonales() + " - " + e.getMessage());
                }

            }
        } catch (Exception e) {
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public Estudiante getEstudianteByUsuario(String codUsuario) throws DataException {
        Estudiante estudiante =repo.getEstudianteByUsuario(codUsuario);
        if(estudiante == null){
            throw new DataException("No se encontro el estudiante");
        }
        return estudiante;
    }

    @Override
    public Estudiante getEstudianteByCodigoUnico(String codUnico) {
        return this.repo.getEstudianteByCodUnicoEstudiante(codUnico);
    }

    @Override
    public DatoPersonal getDatoPersonalByEstudiante(Integer codEstudiante) {
        return dpService.getDatoPersonalByEstudiante(codEstudiante);
    }

    @Override
    public List<EstudianteDto> getEstudiantesPA(List<Estudiante> estudiantes) {
        List<PostulantesValidos> postulantes = postulantesValidosService.getPostulantesValidos();
        List<Estudiante> estudiantesFiltrados = estudiantes.stream()
                .filter(estudiante -> postulantes.stream()
                        .anyMatch(postulante -> postulante.getCedula().equals(
                                this.getDatoPersonalByEstudiante(estudiante.getCodEstudiante()).getCedula()
                        )))
                .collect(Collectors.toList());
        List<EstudianteDto> listDto = new ArrayList<EstudianteDto>();
        for (Estudiante estudiante : estudiantesFiltrados) {
            EstudianteDto objDto = new EstudianteDto();
            DatoPersonal dp = this.getDatoPersonalByEstudiante(estudiante.getCodEstudiante());
            objDto.setCedula(dp.getCedula());
            objDto.setNombre(dp.getNombre() + " " + dp.getApellido());
            objDto.setTelefono(dp.getNumTelefCelular());
            objDto.setCodUnico(estudiante.getCodUnicoEstudiante());
            listDto.add(objDto);
        }

        return listDto;
    }

    @Override
    public List<EstudianteDto> getEstudiantesSinAsignarPA() {
        List<Estudiante> estudiantes = this.getAllWithOutParalelo();
        if (estudiantes.isEmpty()) {
            throw new RuntimeException();

        }
        return this.getEstudiantesPA(estudiantes);
    }

    @Override
    public List<EstudianteDto> getEstudiantesBaja() {

        // para identificar los estudiantes del periodo de formación actual, se cruza postulantes
        // válidos con estudiantes usando dato personal como referencia

        List<Estudiante> estudiantes = this.getEstudiantesIs("BAJA");
        List<PostulantesValidos> postulantes = postulantesValidosService.getPostulantesValidosDiferentBaja();
        List<Estudiante> estudiantesFiltrados = estudiantes.stream()
                .filter(estudiante -> postulantes.stream()
                        .anyMatch(postulante -> postulante.getCedula().equals(
                                this.getDatoPersonalByEstudiante(estudiante.getCodEstudiante()).getCedula()
                        )))
                .collect(Collectors.toList());
        List<EstudianteDto> listDto = new ArrayList<EstudianteDto>();
        for (Estudiante estudiante : estudiantesFiltrados) {
            EstudianteDto objDto = new EstudianteDto();
            DatoPersonal dp = this.getDatoPersonalByEstudiante(estudiante.getCodEstudiante());
            objDto.setCedula(dp.getCedula());
            objDto.setNombre(dp.getNombre() + " " + dp.getApellido());
            objDto.setTelefono(dp.getNumTelefCelular());
            objDto.setCodUnico(estudiante.getCodUnicoEstudiante());
            listDto.add(objDto);
        }

        return listDto;
    }

    @Override
    public Estudiante getEstudianteByNotaFormacion(Integer codNotaFormacion) {
        return repo.getEstudianteByNotaFormacionFinal(codNotaFormacion);
    }

    @Override
    public List<Estudiante> getEstudiantesIs(String Estado) {
        return repo.getAllByEstadoIsIgnoreCase(Estado);
    }

    private String encodePassword(String password) {

        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }

}
