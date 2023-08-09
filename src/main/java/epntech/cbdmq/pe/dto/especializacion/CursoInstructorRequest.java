package epntech.cbdmq.pe.dto.especializacion;

import lombok.Data;

@Data
public class CursoInstructorRequest {

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
    private String tipoProcedencia;
    private String nombreZona;
    private String unidadGestion;
    private String nombreTipoInstructor;

}
