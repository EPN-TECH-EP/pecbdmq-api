package epntech.cbdmq.pe.dominio.profesionalizacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Data
@Entity(name = "pro_periodo")
@Table(name = "pro_periodo")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo SET estado = 'ELIMINADO' WHERE cod_periodo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProPeriodos extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_periodo")
    private Integer codigoPeriodo;

    @Column(name = "nombre_periodo")
    private String nombrePeriodo;

    @Column(name = "fecha_inicio_periodo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @Column(name = "fecha_fin_periodo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;

    @Column(name = "estado")
    private String estado;

}
