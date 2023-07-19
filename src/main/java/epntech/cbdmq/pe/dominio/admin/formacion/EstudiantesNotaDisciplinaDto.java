package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import lombok.Data;

import java.util.List;
@Data
public class EstudiantesNotaDisciplinaDto {
    private List<Paralelo> paralelos;
    private List<EstudiantesNotaDisciplina> estudiantesNotaDisciplina;
}
