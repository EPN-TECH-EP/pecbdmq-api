package epntech.cbdmq.pe.dto;

import lombok.Data;

@Data
public class CursoInstructorDTO {

    private Long codInstructorCurso;
    private Long codCursoEspecializacion;
    private Integer codInstructor;
    private Integer codDatosPersonales;
    private Integer codTipoProcedencia;
    private Integer codEstacion;
    private Integer codUnidadGestion;
    private Integer codTipoContrato;
    private Integer codTipoInstructor;
    private String descripcion;
    private String estado;

}
