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
    private Integer codComponenteNota;
    @Column(name = "nombre_componente_nota")
    private String nombre;
    @Column(name = "estado")
    private String estado;
    @Column(name = "porcentaje_componente_nota")
    private Double porcentajeComponenteNota;
    @Column(name = "cod_periodo_academico")
    private Integer codPeriodoAcademico;

}
