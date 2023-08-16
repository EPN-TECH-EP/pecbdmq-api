package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParalelo;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaParaleloDto;

import java.util.List;

public interface ProSemestreMateriaParaleloService extends ProfesionalizacionService<ProSemestreMateriaParalelo, Integer> {
    List<ProMateriaParaleloDto> getAllByCodSemestreMateria(Integer codigo);

}
