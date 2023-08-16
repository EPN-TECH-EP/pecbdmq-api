package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloInstructor;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloInstructorDto;

import java.util.List;

public interface ProSemestreMateriaParaleloInstructorService extends ProfesionalizacionService<ProSemestreMateriaParaleloInstructor, Integer> {
    List<ProParaleloInstructorDto> getAllByCodMateriaParalelo(Integer codigo);


}
