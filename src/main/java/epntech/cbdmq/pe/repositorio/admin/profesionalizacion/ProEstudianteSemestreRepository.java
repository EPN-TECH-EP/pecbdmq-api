package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProEstudianteSemestre;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import java.util.List;
import java.util.Optional;

public interface ProEstudianteSemestreRepository extends ProfesionalizacionRepository<ProEstudianteSemestre, Integer> {
    Optional<ProEstudianteSemestre> findById(Integer Codigo);
    Optional<ProEstudianteSemestre> findByCodigoEstudianteAndCodigoSemestre(int codigoMateria, int codigoSemestre);
    //@Query("SELECT p1_0.codigoEstudiante, p1_0.codigo, p1_0.codigoSemestre, p1_0.estado, p1_1.codEstudiante,p1_1.codDatosPersonales, p1_1.codUnicoEstudiante FROM ProEstudianteSemestre p1_0 INNER JOIN Estudiante p1_1 ON p1_0.codigoEstudiante=p1_1.codEstudiante where p1_0.codigoSemestre=:codigo")
    List<ProEstudianteSemestre> findByCodigoSemestre(Integer codigo);
}
