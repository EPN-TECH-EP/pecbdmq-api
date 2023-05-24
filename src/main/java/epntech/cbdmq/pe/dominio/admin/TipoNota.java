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
@Entity(name = "gen_tipo_nota")
@Table(name = "gen_tipo_nota")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_nota SET estado = 'ELIMINADO' WHERE cod_tipo_nota = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoNota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod_tipo_nota;
    @Column(name = "tipo_nota")
    private String nota;
    @Column(name = "estado")
   	private String estado;
	@Column(name = "cod_componente_nota")
	private Integer cod_componente_nota;
    
}
