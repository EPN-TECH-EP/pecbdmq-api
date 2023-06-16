package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.EncuestaFormulario;

public interface EncuestaFormularioRepository extends JpaRepository<EncuestaFormulario, Integer>{
     
	
	
	@Query(value="select gef.cod_encuesta_resumen,gef.cod_catalogo_pregunta\r\n"
			+ "from cbdmq.gen_catalogo_pregunta gcp, cbdmq.gen_catalogo_respuesta gcr,cbdmq.gen_encuesta_formulario gef \r\n"
			+ "where gcp.cod_catalogo_pregunta=gef.cod_catalogo_pregunta and\r\n"
			+ "      gcr.cod_catalogo_respuesta =gef.cod_catalogo_respuesta and \r\n"
			+ "     cod_encuesta_resumen=codEncuesta\r\n"
			+ "    group by gef.cod_catalogo_pregunta,gef.cod_encuesta_resumen",nativeQuery=true)
	List<EncuestaFormulario> encuesta(Integer codEncuesta);
}
