package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleData;

public interface PruebaDetalleRepository extends JpaRepository<PruebaDetalle, Integer> {

	Optional<PruebaDetalle> findByCodSubtipoPruebaAndCodPeriodoAcademico(Integer subtipo, Integer periodo);
	
	Optional<PruebaDetalle> findByCodCursoEspecializacionAndCodSubtipoPrueba(Long codCursoEspecializacion, Long subtipo);

	@Query(nativeQuery = true, name = "PruebaDetalle.findDatosPrueba")
	Optional<PruebaDetalleData> getPruebaDetalleDatos(@Param("codCursoEspecializacion") Long codCursoEspecializacion, @Param("codSubTipoPrueba") Long codSubTipoPrueba);
	
	//List<PruebaDetalle> listarTodosConDatosSubTipoPrueba();


}
