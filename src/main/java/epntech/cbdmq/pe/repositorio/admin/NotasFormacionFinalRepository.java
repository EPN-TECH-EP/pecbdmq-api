package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface NotasFormacionFinalRepository extends JpaRepository<NotasFormacionFinal, Integer> {

	@Procedure(value = "cbdmq.calcular_nota_final")
	Integer calcular_nota_final();
	
	@Procedure(value = "cbdmq.calcular_notafinal_x_estudiante")
	Integer calcular_notafinal_x_estudiante(Integer codEstudiante);
	
	@Query(value = "select coalesce(realizo_encuesta, false) "
			+ "from cbdmq.gen_nota_formacion_final "
			+ "where cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and cod_estudiante = :codEstudiante", nativeQuery=true)
	Boolean realizoEncuesta(Long codEstudiante);
	
	@Query(value = "select n.* "
			+ "from cbdmq.gen_nota_formacion_final n"
			+ "where n.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and n.cod_estudiante = :codEstudiante", nativeQuery=true)
	Optional<NotasFormacionFinal> getByEstudiante(Long codEstudiante);
	
	@Modifying
	@Query("UPDATE NotasFormacionFinal n SET n.realizoEncuesta = true WHERE n.codPeriodoAcademico = cbdmq.get_pa_activo() and n.codEstudiante = ?1")
	void actualizarEstadorealizoEncuesta(Long codEstudiante);
}


