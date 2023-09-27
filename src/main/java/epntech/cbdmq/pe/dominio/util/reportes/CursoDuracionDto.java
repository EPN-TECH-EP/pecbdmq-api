package epntech.cbdmq.pe.dominio.util.reportes;

import lombok.Data;

@Data
public class CursoDuracionDto {
    private Long codCursoEspecializacion;
    private String nombre;
    private Integer duracion;
}
