package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.EncuestaResumen;

public interface EncuestaResumenRepository extends JpaRepository<EncuestaResumen, Integer>{

	
	
	
	@Query(value="select  COALESCE( ger.cod_encuesta_resumen,0)\r\n"
			+ "from  cbdmq.gen_encuesta_resumen ger\r\n"
			+ "where  cod_periodo_academico =  cbdmq.get_pa_activo()\r\n"
			+ "and UPPER(ger.estado) = 'ACTIVO' \r\n"
			+ "and current_date  between fecha_inicio and fecha_fin", nativeQuery=true)
	Integer existeEncuesta();
}
