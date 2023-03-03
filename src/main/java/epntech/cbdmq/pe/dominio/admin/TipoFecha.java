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

@Data
@Entity(name = "gen_tipo_fecha")
@Table(name = "gen_tipo_fecha")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_fecha SET estado = 'ELIMINADO' WHERE cod_tipo_fecha = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoFecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod_tipo_fecha;
    @Column(name = "tipo_fecha")
    private String fecha;
    @Column(name = "estado")
   	private String estado;
}
