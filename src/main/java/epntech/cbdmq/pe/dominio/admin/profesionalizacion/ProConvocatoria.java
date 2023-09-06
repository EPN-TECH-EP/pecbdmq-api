package epntech.cbdmq.pe.dominio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Data
@Entity(name = "pro_convocatoria")
@Table(name = "pro_convocatoria")
@SQLDelete(sql = "UPDATE {h-schema}pro_convocatoria SET estado = 'ELIMINADO' WHERE cod_convocatoria = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProConvocatoria extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_convocatoria")
    private Integer codigo;
    @Column(name = "cod_semestre")
    private Integer codigoSemestre;
    @Column(name = "cod_parametro")
    private Integer codigoParametro;
    @Column (name = "cod_parametro2")
    private Integer codigoParametro2;
    @Column(name = "codigo_unico_convocatoria")
    private String codigoUnicoConvocatoria;
    @Column (name = "nombre")
    private String nombre;
    @Column (name = "fecha_inicio")
    private Date fechaInicio;
    @Column (name = "fecha_fin")
    private Date fechaFin;
    @Column (name = "fecha_actual")
    private Date fechaActual;
    @Column(name = "cod_periodo")
    private Integer codPeriodo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "correo")
    private String correo;
}
