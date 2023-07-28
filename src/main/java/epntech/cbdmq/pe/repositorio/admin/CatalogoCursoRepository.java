package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;

public interface CatalogoCursoRepository extends JpaRepository<CatalogoCurso, Integer> {

	Optional<CatalogoCurso> findByNombreCatalogoCursoIgnoreCase(String Nombre);

	List<CatalogoCurso> findByCodTipoCurso(Integer codTipoCurso);
	
}
