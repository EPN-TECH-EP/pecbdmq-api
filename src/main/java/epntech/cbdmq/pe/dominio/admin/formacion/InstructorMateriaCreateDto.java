package epntech.cbdmq.pe.dominio.admin.formacion;

import lombok.Data;

@Data
public class InstructorMateriaCreateDto {
    private Integer codMateria;
    private Integer codParalelo;
    private Integer codAula;
    private Integer codCoordinador;
    private Integer[] codAsistentes;
    private Integer[] codInstructores;
}
