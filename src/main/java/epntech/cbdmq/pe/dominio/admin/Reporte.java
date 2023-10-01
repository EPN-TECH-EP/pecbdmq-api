package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "codigo_reporte")
    private String codigoReporte;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "modulo")
    private String modulo;

    @Column(name = "ver_fechas")
    private Boolean verFechas;

    @Column(name = "ver_select_promocion")
    private Boolean verSelectPromocion;

    @Column(name = "ver_select_periodo")
    private Boolean verSelectPeriodo;

    @Column(name = "ver_select_curso")
    private Boolean verSelectCurso;

}
