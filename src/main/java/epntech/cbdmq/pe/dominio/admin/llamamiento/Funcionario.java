package epntech.cbdmq.pe.dominio.admin.llamamiento;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "gen_funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cod_funcionario")
    private Integer codFuncionario;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cod_datos_personales", referencedColumnName = "cod_datos_personales")
    private DatoPersonal datoPersonal;
    @Column(name="es_operativo")
    private Boolean esOperativo;
    @Column(name="fecha_ingreso")
    private Date fechaIngreso;
    @Column(name="agrupacion")
    private String agrupacion;
    @Column(name="tipo")
    private String tipo;


}

