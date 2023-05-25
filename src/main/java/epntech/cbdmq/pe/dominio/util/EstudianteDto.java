package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Entity
@Table(name = "gen_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}gen_estudiante SET estado = 'ELIMINADO' WHERE cod_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EstudianteDto implements Serializable {
    private static final long serialVersionUID = 1935713547872014437L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer codEstudiante;

    protected Integer codDatosPersonales;
    protected String codigoUnicoEstudiante;
    protected String estado;

    public EstudianteDto(Integer codEstudiante, Integer codDatosPersonales, String codigoUnico, String estado) {
        this.codEstudiante = codEstudiante;
        this.codDatosPersonales = codDatosPersonales;
        this.codigoUnicoEstudiante = codigoUnico;
        this.estado = estado;
    }

    public EstudianteDto() {

    }
}
