package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.util.DelegadoPK;
import org.springframework.data.repository.query.Param;

public interface DelegadoRepository extends JpaRepository<Delegado, DelegadoPK> {

	Optional<Delegado> findBycodUsuario(Integer codUsuario);
		
	Optional<Delegado> findByCodUsuarioAndCodPeriodoAcademico(Integer codUsuario, Integer codPeriodoAcademico);
	@Query(value="select gd.* from cbdmq.gen_delegado gd \n" +
			"left join cbdmq.gen_usuario gu on gd.cod_usuario = gu.cod_usuario \n" +
			"where gu.cod_usuario = :codUsuario\n" +
			"and gu.cod_periodo_academico =: codPA", nativeQuery = true)
	Optional<Delegado> delegadoByUser(@Param("codUsuario") Integer codUsuario,@Param("codPA") Integer codPA);
}
