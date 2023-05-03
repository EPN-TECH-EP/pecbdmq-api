package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;
import epntech.cbdmq.pe.dominio.admin.InscripcionesValidas;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;

public interface InscripcionesValidasRepository extends JpaRepository<InscripcionesValidasUtil, Integer>{

	@Query(value="      select id_postulante\r\n"
			+ "from cbdmq.gen_postulante\r\n"
			+ "where estado in ('VALIDO', 'VALIDO MUESTRA')\r\n"
			+ "and cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
	List<InscripcionesValidasUtil> getinscripcioneslistar();
	
	
	
}
