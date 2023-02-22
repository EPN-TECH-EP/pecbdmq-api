/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Data
@Entity(name = "gen_modulo")
@Table(name = "gen_modulo")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_modulo")
    private Integer cod_modulo;
    @Column(name = "etiqueta")
    private String etiqueta;
    @Column(name = "descripcion")
    private String descripcion;
}
