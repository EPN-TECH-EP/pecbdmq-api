package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloEstudiante;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloEstudianteDto;

import java.util.List;

public interface ProParaleloEstudianteService extends ProfesionalizacionService<ProSemestreMateriaParaleloEstudiante, Integer>{

    List<ProParaleloEstudianteDto> findByMateriaParalelo (Integer codMateriaParalelo);
}
