package epntech.cbdmq.pe.repositorio.admin;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebasFisicas;

public interface ResultadoPruebasFisicasRepository extends JpaRepository<ResultadoPruebasFisicas, Integer> {
	
	Optional<ResultadoPruebasFisicas> findByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba);

	// buscar por codEstudiante y codPrueba
	Optional<ResultadoPruebasFisicas> findByCodEstudianteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba);
	
	
}

