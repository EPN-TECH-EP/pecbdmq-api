package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import epntech.cbdmq.pe.dominio.admin.CatalogoPreguntas;

public interface CatalogoPreguntasRepository extends JpaRepository<CatalogoPreguntas, Integer>{
	
	Optional<CatalogoPreguntas> findByPreguntaIgnoreCase(String Pregunta);

}
