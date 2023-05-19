package epntech.cbdmq.pe.repositorio.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.util.AspirantesDatos;


public interface AspirantesRepository extends JpaRepository<AspirantesDatos, Integer> {
	
	@Procedure(value = "cbdmq.get_aspirantes_femeninos")
	Set<AspirantesDatos> get_aspirantes_femeninos();
	
	@Procedure(value = "cbdmq.get_aspirantes_masculinos")
	Set<AspirantesDatos> get_aspirantes_masculinos();
	

}
