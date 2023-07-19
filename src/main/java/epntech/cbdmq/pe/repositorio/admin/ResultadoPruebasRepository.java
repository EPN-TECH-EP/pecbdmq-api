package epntech.cbdmq.pe.repositorio.admin;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;

public interface ResultadoPruebasRepository extends JpaRepository<ResultadoPruebas, Integer> {

	Optional<ResultadoPruebas> findByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba);	
	
}

