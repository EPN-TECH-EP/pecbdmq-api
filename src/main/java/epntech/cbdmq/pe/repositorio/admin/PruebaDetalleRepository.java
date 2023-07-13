package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface PruebaDetalleRepository extends JpaRepository<PruebaDetalle, Integer> {

	Optional<PruebaDetalle> findByCodSubtipoPruebaAndCodPeriodoAcademico(Integer subtipo, Integer periodo);
	
	Optional<PruebaDetalle> findByCodCursoEspecializacion(Long codCursoEspecializacion);
	@Procedure(value = "cbdmq.get_tipo_resultado")
	String getTipoResultado(@Param("p_cod_subtipo_prueba") Integer p_cod_subtipo_prueba);

	
	//List<PruebaDetalle> listarTodosConDatosSubTipoPrueba();

}
