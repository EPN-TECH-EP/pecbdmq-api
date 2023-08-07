package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultadoPruebasTodoRepository extends JpaRepository<ResultadosPruebasDatos, Integer> {

    @Query(value = "select * from cbdmq.get_approved_applicants(:prueba)", nativeQuery=true)
    List<ResultadosPruebasDatos> get_approved_applicants(Integer prueba);

    @Query(value = "select * from cbdmq.get_all_resultados_prueba(:prueba)", nativeQuery=true)
    Page<ResultadosPruebasDatos> getResultados(Pageable pageable, Integer prueba);

    // lista completa de resultados registrados por prueba
    @Query(value = "select * from cbdmq.get_all_resultados_prueba(:prueba)", nativeQuery=true)
    List<ResultadosPruebasDatos> getResultados(Integer prueba);

    @Query(value = "select * from cbdmq.get_all_resultados_prueba_curso(:prueba, :codCurso)", nativeQuery=true)
    Page<ResultadosPruebasDatos> getResultadosCurso(Pageable pageable, Integer prueba, Integer codCurso);

    @Query(value = "select * from cbdmq.get_all_resultados_prueba_curso(:prueba, :codCurso)", nativeQuery=true)
    List<ResultadosPruebasDatos> getResultados(Integer prueba, Integer codCurso);

}
