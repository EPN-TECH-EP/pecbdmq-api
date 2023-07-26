package epntech.cbdmq.pe.repositorio.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.Documento;

public interface PeriodoAcademicoDocRepository extends JpaRepository<Documento, Integer> {
	
	/*@Query(value="select d.* "
			+ "from cbdmq.gen_convocatoria c, cbdmq.gen_periodo_academico pa, "
			+ "cbdmq.gen_documento d, "
			+ "cbdmq.gen_convocatoria_documento cd "
			+ "where c.cod_periodo_academico = pa.cod_periodo_academico "
			+ "and c.cod_convocatoria = cd.cod_convocatoria "
			+ "and cd.cod_documento = d.cod_documento "
			+ "and UPPER(c.estado) = 'ACTIVO' "
			+ "and UPPER(pa.estado) = 'ACTIVO' "
			+ "and UPPER(d.estado) = 'ACTIVO' "
			//+ "and pa.cod_periodo_academico = :codPeriodo"
			+ "union "
			+ "select d.* "
			+ "from cbdmq.gen_convocatoria c, cbdmq.gen_periodo_academico pa, "
			+ "cbdmq.gen_documento d, "
			+ "cbdmq.gen_periodo_academico_documento pd "
			+ "where c.cod_periodo_academico = pa.cod_periodo_academico "
			+ "and pa.cod_periodo_academico = pd.cod_periodo_academico "
			+ "and d.cod_documento = pd.cod_documento "
			+ "and UPPER(c.estado) = 'ACTIVO' "
			+ "and UPPER(pa.estado) = 'ACTIVO' "
			+ "and UPPER(d.estado) = 'ACTIVO' ", nativeQuery=true)*/
	@Query(value="select d.* \r\n"
			+ "from \r\n"
			+ "cbdmq.gen_documento d, \r\n"
			+ "cbdmq.gen_periodo_academico_documento pd \r\n"
			+ "where pd.cod_periodo_academico =:codPA\r\n"
			+ "and d.cod_documento = pd.cod_documento \r\n"
			+ "and UPPER(d.estado) = 'ACTIVO';", nativeQuery=true)
	Set<Documento> getDocumentos(@Param("codPA") Integer codPA);
	
	@Transactional
    @Modifying
	@Query(value="DELETE FROM cbdmq.gen_periodo_academico_documento WHERE cod_documento = :codDocumento AND cod_periodo_academico= :codPeriodo_academico", nativeQuery=true)
	void deleteByCodDocumento(Integer codPeriodo_academico, Integer codDocumento);

}
