package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacion;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionDto;

import java.util.List;

public interface ProNotaProfesionalizacionService extends ProfesionalizacionService<ProNotaProfesionalizacion, Integer> {

    public List<ProNotasProfesionalizacionDto> findByMateriaParalelo(Integer codMateriaParalelo);
}
