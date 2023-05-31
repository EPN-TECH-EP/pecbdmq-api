package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;

public interface PruebaDetalleRepository extends JpaRepository<PruebaDetalle, Integer>{
	
	Optional<PruebaDetalle> findByCodSubtipoPruebaAndCodPeriodoAcademico(Integer subtipo, Integer periodo);

}

