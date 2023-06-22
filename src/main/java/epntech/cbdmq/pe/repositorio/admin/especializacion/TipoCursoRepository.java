package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;

public interface TipoCursoRepository extends JpaRepository<TipoCurso, Long> {
	Optional<TipoCurso> findByNombreTipoCursoIgnoreCase(String Nombre);

}
