package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import epntech.cbdmq.pe.dominio.util.InscritosValidos;


@Repository
public interface PruebasRepository extends JpaRepository<ResultadosPruebasDatos, Integer> {

	@Query(value = "select * from cbdmq.get_approved_by_test_esp(:codSubTipoPrueba, :codCursoEspecializacion)", nativeQuery = true)
	List<ResultadosPruebasDatos> get_approved_by_test_esp(Long codSubTipoPrueba, Long codCursoEspecializacion);

	//List<InscritosValidos> get_approved_by_test_esp(Long codSubTipoPrueba, Long codCursoEspecializacion);

	
}
