package epntech.cbdmq.pe.repositorio.admin;


import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.CatalogoCursosEspecialización;

public interface CatalogoCursosEspecializaciónRepository extends JpaRepository<CatalogoCursosEspecialización, Integer> {

	//Optional<CatalogoCursosEspecialización> findByNombreIgnoreCase(String Nombre);
	
	
	
}
