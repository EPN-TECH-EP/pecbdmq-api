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
@Entity
@Table(name = "gen_tipo_falta")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_falta SET estado = 'ELIMINADO' WHERE cod_tipo_falta = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoFalta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer codTipoFalta;
    @Column(name = "nombre_falta")
    private String nombreFalta;
    @Column(name = "estado")
	private String estado;
}
