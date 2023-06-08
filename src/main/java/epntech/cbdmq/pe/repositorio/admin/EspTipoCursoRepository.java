package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.EspTipoCurso;

public interface EspTipoCursoRepository extends JpaRepository<EspTipoCurso, Integer> {

	
	//Optional<EspTipoCurso> findByNombreIgnoreCase(String Nombre);
	
}
