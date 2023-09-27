package epntech.cbdmq.pe.repositorio;

import epntech.cbdmq.pe.dominio.admin.ReporteParametro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteParametroRepository extends JpaRepository<ReporteParametro, Long> {

	List<ReporteParametro> findByCodigoReporte(Long codigoReporte);

}
