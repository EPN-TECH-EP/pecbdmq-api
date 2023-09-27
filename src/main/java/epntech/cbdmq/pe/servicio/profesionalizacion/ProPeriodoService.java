package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;

import java.util.Date;
import java.util.List;

public interface ProPeriodoService extends ProfesionalizacionService<ProPeriodos, Integer>{

    List<ProPeriodos> findByEstado(String estado);
    List<ProPeriodos> findByFechaInicioBetween(Date startDate, Date endDate);
}
