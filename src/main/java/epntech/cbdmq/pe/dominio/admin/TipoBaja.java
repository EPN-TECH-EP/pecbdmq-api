/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

@Entity(name = "gen_tipo_baja")
@Table(name = "gen_tipo_baja")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_baja SET estado = 'ELIMINADO' WHERE cod_tipo_baja = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoBaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_tipo_baja")
    private Integer cod_tipo_baja;
    @Column(name = "tipo_baja")
    private String baja;
    @Column(name = "estado")
	private String estado;
}
