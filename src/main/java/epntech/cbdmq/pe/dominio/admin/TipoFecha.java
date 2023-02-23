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

@Data
@Entity(name = "gen_tipo_fecha")
@Table(name = "gen_tipo_fecha")
public class TipoFecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod_tipo_fecha;
    @Column(name = "tipo_fecha")
    private String fecha;
}
