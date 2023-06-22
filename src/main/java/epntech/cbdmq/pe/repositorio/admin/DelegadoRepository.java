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
		
	Optional<Delegado> findByCodUsuarioAndCodPeriodoAcademico(Integer codUsuario, Integer codPeriodoAcademico);
	
}
