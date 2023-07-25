package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Sanciones;


public interface SancionesRepository extends JpaRepository<Sanciones, Integer>{

    List<Sanciones> findAllByCodEstudiante(Integer codEstudiante);

	
}
