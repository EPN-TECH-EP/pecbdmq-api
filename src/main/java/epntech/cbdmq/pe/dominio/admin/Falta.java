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
@Entity
@Table(name = "gen_falta")

@SQLDelete(sql = "UPDATE {h-schema}gen_falta SET estado = 'ELIMINADO' WHERE cod_falta = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

public class Falta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_falta")
	private Integer cod_falta;
	
	@Column(name = "nombre_falta")
	private String nombre_falta;
	
	@Column(name = "estado")
	private String estado;
	
	
}
