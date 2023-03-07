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
@Entity(name = "gen_componente_nota")
@Table(name = "gen_componente_nota")
@SQLDelete(sql = "UPDATE {h-schema}gen_componente_nota SET estado = 'ELIMINADO' WHERE cod_componente_nota = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ComponenteNota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_componente_nota")
    private Integer cod_componente_nota;
    @Column(name = "tipo_componente_nota")
    private String componentenota;
    @Column(name = "estado")
	private String estado;
}
