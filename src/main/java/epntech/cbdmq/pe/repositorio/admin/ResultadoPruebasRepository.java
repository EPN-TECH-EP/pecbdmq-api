package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;

public interface ResultadoPruebasRepository extends JpaRepository<ResultadoPruebas, Integer> {

	Optional<ResultadoPruebas> findByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba);
	
}
