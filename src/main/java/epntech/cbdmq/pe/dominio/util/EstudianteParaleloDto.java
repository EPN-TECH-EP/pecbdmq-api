package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import lombok.Data;

import java.util.List;

@Data
public class EstudianteParaleloDto {
    List<EstudianteDto> lista;
    Integer codParalelo;

}
