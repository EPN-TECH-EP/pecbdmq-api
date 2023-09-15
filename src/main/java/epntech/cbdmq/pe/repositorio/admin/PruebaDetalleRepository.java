package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleData;

public interface PruebaDetalleRepository extends JpaRepository<PruebaDetalle, Integer> {

	@Query(value = "select p1_0.cod_prueba_detalle,p1_0.cod_curso_especializacion,p1_0.cod_periodo_academico,p1_0.cod_subtipo_prueba,p1_0.descripcion_prueba,p1_0.estado,p1_0.fecha_fin,p1_0.fecha_inicio,p1_0.hora,p1_0.orden_tipo_prueba,p1_0.puntaje_maximo,p1_0.puntaje_minimo,p1_0.tiene_puntaje from cbdmq.gen_prueba_detalle p1_0 where p1_0.cod_subtipo_prueba=:cod_subtipo_prueba and p1_0.cod_periodo_academico=:cod_periodo_academico", nativeQuery = true)
	Optional<PruebaDetalle> findByCodSubtipoPruebaAndCodPeriodoAcademico(@Param("cod_subtipo_prueba")Integer subtipo, @Param("cod_periodo_academico")Integer periodo);

	Optional<PruebaDetalle> findByCodCursoEspecializacion(Long codCursoEspecializacion);


	@Procedure(value = "cbdmq.get_tipo_resultado")
	String getTipoResultado(@Param("p_cod_subtipo_prueba") Integer p_cod_subtipo_prueba);

	// tipo de resultado por curso
	//cbdmq.get_tipo_resultado_curso(p_cod_subtipo_prueba integer, p_cod_curso integer)
	@Procedure(value = "cbdmq.get_tipo_resultado_curso")
	String getTipoResultadoCurso(@Param("p_cod_subtipo_prueba") Integer p_cod_subtipo_prueba, @Param("p_cod_curso") Integer p_cod_curso);



	//List<PruebaDetalle> listarTodosConDatosSubTipoPrueba();

	Optional<PruebaDetalle> findByCodCursoEspecializacionAndCodSubtipoPrueba(Integer codCursoEspecializacion, Integer codSubtipoPrueba);


	@Query(nativeQuery = true, name = "PruebaDetalle.findDatosPrueba")
	Optional<PruebaDetalleData> getPruebaDetalleDatos(@Param("codCursoEspecializacion") Long codCursoEspecializacion, @Param("codSubTipoPrueba") Long codSubTipoPrueba);



}
