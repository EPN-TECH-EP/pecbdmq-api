package epntech.cbdmq.pe.repositorio.admin;


import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.admin.DelegadoPK;

public interface DelegadoRepository extends JpaRepository<Delegado, DelegadoPK> {

	
}
