package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import lombok.Data;

@Data
public class DatoPersonalEstudianteDto {
    private DatoPersonal datoPersonal;
    private Estudiante estudiante;
    private Boolean esCiudadano = false;
    private Boolean esFuncionario= false;

}
