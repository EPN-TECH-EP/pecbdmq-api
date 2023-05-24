package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_parametriza_prueba_resumen")

public class ParametrizaPruebaResumen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_parametriza_prueba_resumen")
	private Integer cod_parametriza_prueba_resumen;
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fecha_creacion;
	
	@Column(name = "fecha_inicio")
	private LocalDateTime fecha_inicio;
	
	@Column(name = "fecha_fin")
	private LocalDateTime fecha_fin;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private String estado;
	
	
	
	
	
	
	
	
	

}
