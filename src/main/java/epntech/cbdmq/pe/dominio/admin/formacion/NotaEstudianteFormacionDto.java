package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import lombok.Data;

import java.util.List;
@Data
public class NotaEstudianteFormacionDto {
    private List<Paralelo> paralelos;
    private List<EstudianteDatos> estudianteDatos;
}
