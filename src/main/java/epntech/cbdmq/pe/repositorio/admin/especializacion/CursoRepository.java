package epntech.cbdmq.pe.repositorio.admin.especializacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Procedure(value = "cbdmq.update_estado_curso")
	int updateEstadoProceso(Long estado, Long codCurso);
	
}
