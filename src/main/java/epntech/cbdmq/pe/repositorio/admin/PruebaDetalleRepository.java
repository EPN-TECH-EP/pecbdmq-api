package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;

public interface PruebaDetalleRepository extends JpaRepository<PruebaDetalle, Integer> {

	Optional<PruebaDetalle> findByCodSubtipoPruebaAndCodPeriodoAcademico(Integer subtipo, Integer periodo);
	
	Optional<PruebaDetalle> findByCodCursoEspecializacion(Long codCursoEspecializacion);

	
	//List<PruebaDetalle> listarTodosConDatosSubTipoPrueba();


}
