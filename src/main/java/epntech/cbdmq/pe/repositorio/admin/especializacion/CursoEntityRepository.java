package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import epntech.cbdmq.pe.dominio.util.CursoDatos;

public interface CursoEntityRepository extends JpaRepository<CursoDatos, Long> {

	@Query(nativeQuery = true, value = "select c.cod_curso_especializacion, c.cod_aula, c.numero_cupo,\n"
			+ "c.fecha_inicio_curso, c.fecha_fin_curso, c.nota_minima,\n"
			+ "c.tiene_modulos, c.cod_tipo_curso, cc.nombre_catalogo_curso \n"
			+ "from cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc\n"
			+ "where c.cod_catalogo_cursos = cc.cod_catalogo_cursos \n"
			+ "and upper(c.estado) = 'ACTIVO'\n"
			+ "and upper(cc.estado) = 'ACTIVO'\n"
			+ "and cod_curso_especializacion = :codCursoEspecializacion ")
	Optional<CursoDatos> getCursoDatos(Long codCursoEspecializacion);
}
