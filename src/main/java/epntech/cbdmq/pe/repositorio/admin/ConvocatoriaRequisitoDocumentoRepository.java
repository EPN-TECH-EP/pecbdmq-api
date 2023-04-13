package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaRequisistoDocumento;
import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;

public interface ConvocatoriaRequisitoDocumentoRepository extends JpaRepository<ConvocatoriaRequisistoDocumento, Integer> {

	@Query(value=" select gc.codigo_unico_convocatoria,\r\n"
			+ " 		gc.fecha_inicio_convocatoria,\r\n"
			+ " 		gc.fecha_fin_convocatoria,\r\n"
			+ " 		gc.hora_inicio_convocatoria,\r\n"
			+ " 		gc.hora_fin_convocatoria,\r\n"
			+ " 		gc.cupo_hombres,\r\n"
			+ " 		gc.cupo_mujeres,\r\n"
			+ " 		gd.nombre_documento,\r\n"
			+ " 		gd.ruta,\r\n"
			+ " 		gr.nombre_requisito,\r\n"
			+ " 		gr.descripcion_requisito \r\n"
			+ " from cbdmq.gen_convocatoria gc,\r\n"
			+ "      cbdmq.gen_documento gd,\r\n"
			+ "      cbdmq.gen_requisito gr,\r\n"
			+ "      cbdmq.gen_convocatoria_documento gcd, \r\n"
			+ "      cbdmq.gen_convocatoria_requisito gcr\r\n"
			+ "where gcd.cod_convocatoria = gc.cod_convocatoria \r\n"
			+ "and gcd.cod_documento =gd.cod_documento \r\n"
			+ "and gcr.cod_convocatoria =gc.cod_convocatoria \r\n"
			+ "and gcr.cod_requisito  =gr.cod_requisito \r\n"
			+ "  and UPPER(gc.estado) != 'ELIMINADO'\r\n"
			+ "  and upper(gd.estado) ='ACTIVO'\r\n"
			+ "  and upper(gr.estado) ='ACTIVO'", nativeQuery=true)
	List<ConvocatoriaRequisistoDocumento> getConvocatoriaRequisistoDocumentos();
	
	
}
