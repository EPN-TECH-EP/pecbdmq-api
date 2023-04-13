package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;
import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;

public interface ConvocatorialistarRepository extends JpaRepository<Convocatorialistar, Integer> {

	@Query(value="      select \r\n"
			+ " 		gc.cod_convocatoria,\r\n"
			+ " 		gc.nombre_convocatoria, \r\n"
			+ " 		gc.codigo_unico_convocatoria,\r\n"
			+ " 		gc.fecha_inicio_convocatoria,\r\n"
			+ " 		gc.fecha_fin_convocatoria,\r\n"
			+ " 		gc.hora_inicio_convocatoria,\r\n"
			+ " 		gc.hora_fin_convocatoria,\r\n"
			+ " 		gc.cupo_hombres,\r\n"
			+ " 		gc.cupo_mujeres,\r\n"
			+ " 		gd.ruta  \r\n"
			+ " from cbdmq.gen_convocatoria gc,\r\n"
			+ "      cbdmq.gen_documento gd,\r\n"
			+ "	  cbdmq.gen_convocatoria_documento gcd      \r\n"
			+ " where gcd.cod_convocatoria = gc.cod_convocatoria \r\n"
			+ " and gcd.cod_documento =gd.cod_documento \r\n"
			+ "  and UPPER(gc.estado) != 'ELIMINADO'\r\n"
			+ "  and upper(gd.estado) ='ACTIVO'", nativeQuery=true)
	List<Convocatorialistar> getConvocatorialistar();
	
	
}
