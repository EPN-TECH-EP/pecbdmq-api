package epntech.cbdmq.pe.dominio.admin.llamamiento;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import lombok.Data;

@Data
public class DatosSincronizados {
    private DatoPersonal datoPersonal;
    private Boolean deApiFuncionario;
}
