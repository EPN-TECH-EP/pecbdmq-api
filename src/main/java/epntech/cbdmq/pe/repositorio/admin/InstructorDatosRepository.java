package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.InstructorDatos;

public interface InstructorDatosRepository extends JpaRepository<InstructorDatos, Long> {

	@Query(value = "select i.cod_instructor, "
			+ "tp.cod_tipo_procedencia, tp.tipo_procedencia, "
			+ "e.cod_estacion, e.nombre_zona, "
			+ "ug.cod_unidad_gestion, ug.unidad_gestion, "
			+ "tc.cod_tipo_contrato, tc.nombre_tipo_contrato, "
			+ "dp.cedula, dp.nombre, dp.apellido, dp.correo_personal "
			+ "from cbdmq.gen_instructor i, cbdmq.gen_dato_personal dp, cbdmq.gen_instructor_periodo p, "
			+ "cbdmq.gen_tipo_procedencia tp, cbdmq.gen_estacion_trabajo e, "
			+ "cbdmq.gen_unidad_gestion ug, cbdmq.gen_tipo_contrato tc "
			+ "where i.cod_datos_personales = dp.cod_datos_personales "
			+ "and i.cod_instructor = p.cod_instructor "
			+ "and i.cod_tipo_procedencia = tp.cod_tipo_procedencia "
			+ "and i.cod_estacion = e.cod_estacion "
			+ "and i.cod_unidad_gestion = ug.cod_unidad_gestion "
			+ "and i.cod_tipo_contrato = tc.cod_tipo_contrato "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(tp.estado) = 'ACTIVO' "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(ug.estado) = 'ACTIVO' "
			+ "and upper(tc.estado) = 'ACTIVO' "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery = true)
	List<InstructorDatos> getAllInstructorDatos();
}

