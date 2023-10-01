package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.admin.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReporteRepository extends JpaRepository<Reporte, Long>{

	Optional<Reporte> findByCodigoReporte(String codigoReporte);

}
