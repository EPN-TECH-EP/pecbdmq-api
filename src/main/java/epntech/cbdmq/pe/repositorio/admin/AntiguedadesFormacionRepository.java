package epntech.cbdmq.pe.repositorio.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;

public interface AntiguedadesFormacionRepository extends JpaRepository<AntiguedadesFormacion, String> {

	@Query(value = "select e.codigo_unico_estudiante, dp.cedula, dp.nombre, dp.apellido, dp.correo_personal, nf.nota_final "
			+ "from cbdmq.gen_nota_formacion_final nf, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp "
			+ "where nf.cod_estudiante = e.cod_estudiante "
			+ "and e.cod_datos_personales = dp.cod_datos_personales "
			+ "and nf.aprobado = true "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and nf.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by nf.nota_final desc", nativeQuery=true)
	Set<AntiguedadesFormacion> getAntiguedadesFormacion();
}
