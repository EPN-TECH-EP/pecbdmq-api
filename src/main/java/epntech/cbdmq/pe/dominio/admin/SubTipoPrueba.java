package epntech.cbdmq.pe.dominio.admin;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_subtipo_prueba")
@SQLDelete(sql = "UPDATE {h-schema}gen_subtipo_prueba SET estado = 'ELIMINADO' WHERE cod_subtipo_prueba = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class SubTipoPrueba {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_subtipo_prueba")
	private Integer codSubtipoPrueba;
	
	@Column(name = "cod_tipo_prueba")
	private Integer codTipoPrueba;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;
}
