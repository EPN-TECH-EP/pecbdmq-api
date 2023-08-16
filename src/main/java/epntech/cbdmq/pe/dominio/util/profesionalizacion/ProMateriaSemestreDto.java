package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProMateriaSemestreDto {
    @Id
    private Integer CodMateriaSemestre;
    private Integer CodPeriodoSemestre;
    private Integer CodMateria;
    private Integer CodPeriodo;
    private Integer CodSemestre;
    private Integer CodAula;

    private String NombrePeriodo;
    private String NombreSemestre;
    private String NombreMateria;
    private String NombreAula;
    private double NumeroHoras;
    private double NotaMinima;
    private double NotaMaxima;
    private Boolean esProyecto;
}
