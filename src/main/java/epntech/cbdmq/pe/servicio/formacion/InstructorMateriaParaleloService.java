package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;

import java.util.List;

public interface InstructorMateriaParaleloService {
    public List<InstructorMateriaParalelo> getInstructoresMateriaParalelo();

    public InstructorMateriaParalelo save(InstructorMateriaParalelo newObj);

    public Boolean asignarInstructorMateriaParaleloAll(Integer codMateria, Integer codCoordinador, Integer codAula, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo);
    public Boolean asignarInstructortoMateriaParalelo(Integer codMateria, Integer codCoordinador, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo);

    List<InstructorDatos> getInstructoresAsistentes(Integer codMateriaParalelo);

    List<InstructorDatos> getInstructores(Integer codMateriaParalelo);

    InstructorDatos getCoordinador(Integer codMateriaParalelo);
    public List<InstructorMateriaReadDto> getMateriaInfoDto();
    public List<InformacionMateriaDto> getInformacionMateriaDto();
    public Boolean actualizarInstructorMateriaParalelo(Integer codMateria, Integer codCoordinador, Integer codAula, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo);

}
