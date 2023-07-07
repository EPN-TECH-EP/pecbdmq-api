package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import lombok.Data;

import java.util.List;

@Data
public class MateriaAulaParaleloUtil {
    private List<MateriaAulaUtil> materiasAulas;
    private List<Paralelo> paralelos;
}
