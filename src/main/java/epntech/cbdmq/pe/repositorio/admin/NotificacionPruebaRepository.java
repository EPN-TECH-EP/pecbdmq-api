package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.NotificacionPrueba;

public interface NotificacionPruebaRepository extends JpaRepository<NotificacionPrueba, Integer>{

	
	Optional<NotificacionPrueba>findBymensaje(String mensaje); 	
	
	
	
	
}
