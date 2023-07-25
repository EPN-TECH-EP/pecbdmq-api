package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.util.TipoFaltaPeriodoUtil;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.FaltaPeriodo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaltaPeriodoRepository extends JpaRepository<FaltaPeriodo, Integer>{

@Query(name="TipoFaltaPeriodoUtil.get",nativeQuery=true)
List<TipoFaltaPeriodoUtil> getFaltasPeriodo();
}
