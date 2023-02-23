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
@Entity(name = "gen_tipo_sancion")
@Table(name = "gen_tipo_sancion")
public class TipoSancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod_tipo_sancion;
    @Column(name = "tipo_sancion")
    private String sancion;
}
