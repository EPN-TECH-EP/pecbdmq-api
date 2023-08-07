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
			"and UPPER(d.estado) = 'ACTIVO'", nativeQuery=true)
	Set<Documento> getDocumentosEspecializacion(@Param("codCurso") Integer codCurso);

}
