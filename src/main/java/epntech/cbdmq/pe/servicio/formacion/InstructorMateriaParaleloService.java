package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;

import java.util.List;

public interface InstructorMateriaParaleloService {
    public List<InstructorMateriaParalelo> getInstructoresMateriaParalelo();

    public InstructorMateriaParalelo save(InstructorMateriaParalelo newObj);

    public Boolean asignarInstructorMateriaParalelo(Integer codMateria, Integer codCoordinador, Integer codAula, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo);

    List<InstructorDatos> getInstructoresAsistentes(Long codMateriaParalelo);

    List<InstructorDatos> getInstructores(Long codMateriaParalelo);

    InstructorDatos getCoordinador(Long codMateriaParalelo);
    public List<InstructorMateriaReadDto> getMateriaInfoDto();
    public List<InformacionMateriaDto> getInformacionMateriaDto();

}
