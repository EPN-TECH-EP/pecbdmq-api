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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "gen_tipo_prueba")
@Table(name = "gen_tipo_prueba")
public class TipoPrueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod_tipo_prueba;
    @Column(name = "tipo_prueba")
    private String Prueba;
}
