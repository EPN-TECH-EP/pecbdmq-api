package epntech.cbdmq.pe.dto.especializacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CursoInstructorResponse {
    @Id
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
    private String nombreTipoContrato;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private String nombreCatalogoCurso;

}
