package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;
import epntech.cbdmq.pe.dominio.admin.InscripcionesValidas;

public interface InscripcionesValidasRepository extends JpaRepository<InscripcionesValidas, Integer>{

	@Query(value="      select id_postulante\r\n"
			+ "from cbdmq.gen_postulante\r\n"
			+ "where estado in ('VALIDO', 'VALIDO MUESTRA')", nativeQuery=true)
	List<InscripcionesValidas> getinscripcioneslistar();
	
	
	
}
