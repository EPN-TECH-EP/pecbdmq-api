package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloEstudiante;

import java.util.Optional;

public interface ProParaleloEstudianteRepository extends ProfesionalizacionRepository<ProSemestreMateriaParaleloEstudiante, Integer> {
    Optional<ProSemestreMateriaParaleloEstudiante> findByCodSemestreMateriaParaleloAndCodEstudiante(Integer codSemestreMateriaParalelo, Integer codEstudiante);
}
