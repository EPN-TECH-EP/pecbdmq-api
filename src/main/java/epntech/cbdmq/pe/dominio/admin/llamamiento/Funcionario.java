package epntech.cbdmq.pe.dominio.admin.llamamiento;

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
    @Column(name="es_operativo")
    private Boolean operativo;
    @Column(name="fecha_ingreso")
    private Date fechaIngreso;
    @Column(name="agrupacion")
    private String agrupacion;
    @Column(name="tipo")
    private String type;
    @Column(name="cedula")
    private String pin;
    @Column(name="nombres")
    private String nombres;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="email")
    private String email;
}

