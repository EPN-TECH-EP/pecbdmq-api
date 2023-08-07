package epntech.cbdmq.pe.repositorio.admin.especializacion;


import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoUtilEsp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegadoEspRepository extends JpaRepository<DelegadoEsp, Long> {

	Optional<DelegadoEsp> findByCodUsuario(Integer codUsuario);

	@Query(nativeQuery = true, name = "DelegadoEsp.findDelegados")
	List<DelegadoUtilEsp> findDelegados();

	@Query(value="select gd.* from cbdmq.esp_delegado ed " +
			"left join cbdmq.gen_usuario gu on ed.cod_usuario = gu.cod_usuario " +
			"where gu.cod_usuario = :codUsuario", nativeQuery = true)
	Optional<DelegadoEsp> delegadoByUser(@Param("codUsuario") Integer codUsuario);
}
