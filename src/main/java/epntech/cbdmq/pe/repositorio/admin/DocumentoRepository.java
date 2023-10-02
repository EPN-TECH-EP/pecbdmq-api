package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

	Optional<Documento> findByNombre(String nombre);
	
	
	@Query(value="select d.* \r\n"
			+ "from \r\n"
			+ "cbdmq.gen_documento d, \r\n"
			+ "cbdmq.gen_documento_prueba pd \r\n"
			+ "where pd.cod_prueba_detalle =:codPruebaDetalle\r\n"
			+ "and d.cod_documento = pd.cod_documento \r\n"
			+ "and UPPER(d.estado) = 'ACTIVO';", nativeQuery=true)
	Set<Documento> getDocumentosPruebaDetalle(@Param("codPruebaDetalle") Integer codPruebaDetalle);
	List<Documento> findAllByNombre(String nombre);
	@Query(value="select d.* \n" +
			"from cbdmq.esp_curso_documento ec \n" +
			"left join cbdmq.gen_documento d on ec.cod_documento = d.cod_documento \n" +
			"where ec.cod_curso_especializacion=:codCurso\n" +
			"and UPPER(d.estado) = 'ACTIVO' order by d.cod_documento ", nativeQuery=true)
	Set<Documento> getDocumentosEspecializacion(@Param("codCurso") Integer codCurso);
	@Query(value="select d.* \n" +
			"from cbdmq.esp_curso_documento ec \n" +
			"left join cbdmq.gen_documento d on ec.cod_documento = d.cod_documento \n" +
			"where ec.cod_curso_especializacion=:codCurso\n" +
			"and UPPER(d.estado) = 'ACTIVO' and ec.es_tarea =:esTarea " +
			"order by d.cod_documento" , nativeQuery=true)
	Set<Documento> getTareasEspecializacion(@Param("codCurso") Integer codCurso, @Param("esTarea") Boolean esTarea);

	@Query(value="select gd.* from cbdmq.gen_materia_paralelo_documento gmpd \n" +
			"left join cbdmq.gen_documento gd on gmpd.cod_documento = gd.cod_documento \n" +
			"where gmpd.cod_materia_paralelo =:codMateriaParalelo\n" +
			"order by gd.cod_documento", nativeQuery=true)
	Set<Documento> getDocumentosMateriaFormacion(@Param("codMateriaParalelo") Integer codMateriaParalelo);
	@Query(value="select gd.* from cbdmq.gen_materia_paralelo_documento gmpd \n" +
			"left join cbdmq.gen_documento gd on gmpd.cod_documento = gd.cod_documento \n" +
			"where gmpd.cod_materia_paralelo =:codMateriaParalelo and gmpd.es_tarea =:esTarea \n" +
			"order by gd.cod_documento", nativeQuery=true)
	Set<Documento> getTareasMateriaFormacion(@Param("codMateriaParalelo") Integer codMateriaParalelo, @Param("esTarea") Boolean esTarea);

}
