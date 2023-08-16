package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pro_inscripcion_documento")
public class ProInscripcionDocumento {
    @Id
    @Column(name = "cod_inscripcion")
    private Integer codInscripcion;

    @Column(name = "cod_documento")
    private Integer codDocumento;

    @Column(name = "estado")
    private String estado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ruta")
    private String ruta;
}
