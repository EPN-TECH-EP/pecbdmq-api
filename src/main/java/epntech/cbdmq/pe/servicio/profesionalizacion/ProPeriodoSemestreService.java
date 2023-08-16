package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoSemestreDto;

import java.util.List;

public interface ProPeriodoSemestreService extends ProfesionalizacionService<ProPeriodoSemestre, Integer> {
    List<ProPeriodoSemestreDto> getAllByPeriodo(Integer codigo);
}
