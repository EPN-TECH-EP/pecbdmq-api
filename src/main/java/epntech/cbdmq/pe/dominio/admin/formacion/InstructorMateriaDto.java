package epntech.cbdmq.pe.dominio.admin.formacion;

import lombok.Data;

@Data
public class InstructorMateriaDto {
    private Integer codMateria;
    private Integer codParalelo;
    private Integer codAula;
    private Integer codCoordinador;
    private Integer[] codAsistente;
    private Integer[] codInstructor;
}
