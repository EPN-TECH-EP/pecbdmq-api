package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.util.DelegadoPK;

public interface DelegadoRepository extends JpaRepository<Delegado, DelegadoPK> {

	Optional<Delegado> findBycodUsuario(Integer codUsuario);
	/*@Query(value = "select gd.cod_usuario \r\n"
			+ "from cbdmq.gen_delegado gd, cbdmq.gen_periodo_academico gpa \r\n"
			+ "where gd.cod_periodo_academico  =gpa.cod_periodo_academico\r\n"
			+ "and UPPER(gd.estado) = 'ACTIVO' ", nativeQuery=true)
	Integer getByUsuario(Integer codUsuario);*/
	
	Optional<Delegado> findByCodUsuarioAndCodPeriodoAcademico(Integer codUsuario, Integer codPeriodoAcademico);
	
}
