package epntech.cbdmq.pe.dto.especializacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotasEspecializacionDTO {
    private Integer codEstudiante;
    private Integer codCursoEspecializacion;
    private Integer codNotaEspecializacion;
    private String codigoUnicoEstudiante;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private String correoInstitucional;
    private Double notaFinalEspecializacion;
    private Double notaSupletorio;
    private Integer codInscripcion;
    private Integer codInstructor;
    private Double notaMinima;
}
