package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;

public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Integer> {

	Optional<Convocatoria> findByNombreIgnoreCase(String Nombre);
	
	@Procedure(value = "cbdmq.get_id")
	String getId(String proceso);
	
	@Query(value="select\r\n"
			+ "	c.*\r\n"
			+ "from\r\n"
			+ "	cbdmq.gen_convocatoria c,\r\n"
			+ "	cbdmq.gen_periodo_academico pa,\r\n"
			+ "	cbdmq.gen_convocatoria_requisito cr\r\n"
			+ "where\r\n"
			+ "	c.cod_periodo_academico = pa.cod_periodo_academico\r\n"
			+ "	and c.cod_convocatoria = cr.cod_convocatoria\r\n"
			+ "	and UPPER(c.estado) = 'ACTIVO'\r\n"
			+ "	and UPPER(pa.estado) = 'ACTIVO' ", nativeQuery=true)
	Set<Convocatoria> getConvocatoriaActiva();
	
	@Query(value="select c.* "
			+ "from cbdmq.gen_convocatoria c "
			+ "where c.cod_periodo_academico =  cbdmq.get_pa_activo() "
			+ "and UPPER(c.estado) = 'ACTIVO' ", nativeQuery=true)
	Convocatoria getConvocatoriapaactivo();
	
	@Procedure(value = "cbdmq.get_convocatoria_activa_formacion")
	Integer getConvocatoriaActivaFormacion();

@Procedure(value = "cbdmq.get_id_next")
			String findNextLastCodigo();

}
