package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.NotasDatosFormacion;

public interface NotasDatosFormacionRepository extends JpaRepository<NotasDatosFormacion, String> {

	@Query(value = "select dp.cedula, dp.nombre, dp.apellido, "
			+ "m.nombre_materia, n.nota_materia, n.nota_disciplina, n.nota_supletorio "
			+ "from cbdmq.gen_nota_formacion n, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.gen_materia m "
			+ "where n.cod_estudiante = e.cod_estudiante "
			+ "and e.cod_datos_personales = dp.cod_datos_personales "
			+ "and n.cod_materia = m.cod_materia "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(m.estado) = 'ACTIVO' "
			+ "and n.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and e.cod_estudiante = :codEstudiante", nativeQuery = true)
	List<NotasDatosFormacion> getNotasEstudiante(Long codEstudiante);
	
	@Query(value = "select dp.cedula, dp.nombre, dp.apellido, "
			+ "m.nombre_materia, n.nota_materia, n.nota_disciplina, n.nota_supletorio "
			+ "from cbdmq.gen_nota_formacion n, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.gen_materia m "
			+ "where n.cod_estudiante = e.cod_estudiante "
			+ "and e.cod_datos_personales = dp.cod_datos_personales "
			+ "and n.cod_materia = m.cod_materia "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(m.estado) = 'ACTIVO' "
			+ "and n.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and m.cod_materia = :codMateria", nativeQuery = true)
	List<NotasDatosFormacion> getNotasMateria(Long codMateria);
}
