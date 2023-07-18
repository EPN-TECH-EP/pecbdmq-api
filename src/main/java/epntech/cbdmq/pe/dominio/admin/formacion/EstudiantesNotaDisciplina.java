package epntech.cbdmq.pe.dominio.admin.formacion;

import lombok.Data;

@Data
public class EstudiantesNotaDisciplina {
    private Integer codEstudiante;
    private String codUnico;
    private String nombreCompleto;
    private String cedula;
    private Double promedioDisciplinaOficialSemana;
    private Integer codParalelo;


    public EstudiantesNotaDisciplina(Integer codEstudiante, String codUnico, String nombreCompleto, String cedula, Integer codParalelo) {
        this.codEstudiante = codEstudiante;
        this.codUnico = codUnico;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.codParalelo = codParalelo;
        this.promedioDisciplinaOficialSemana=null;
    }
}
