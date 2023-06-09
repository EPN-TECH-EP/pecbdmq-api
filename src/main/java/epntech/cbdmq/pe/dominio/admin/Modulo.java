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
@Entity(name = "gen_modulo")
@Table(name = "gen_modulo")
@SQLDelete(sql = "UPDATE {h-schema}gen_modulo SET estado = 'ELIMINADO' WHERE cod_modulo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_modulo")
    private Integer codModulo;
    @Column(name = "etiqueta")
    private String etiqueta;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
	private String estado;
}
