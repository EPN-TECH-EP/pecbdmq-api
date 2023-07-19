package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import lombok.Data;

@Data
public class EstudianteNotaFinalDto {
    private NotasFormacionFinal notasFormacionFinal;
    private DatoPersonal datoPersonal;
}
