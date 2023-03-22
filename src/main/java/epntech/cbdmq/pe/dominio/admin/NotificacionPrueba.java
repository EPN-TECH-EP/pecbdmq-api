package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity(name = "gen_notificacion")
@Table(name = "gen_notificacion")

public class NotificacionPrueba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_notifica")
	private Integer cod_notifica;
	@Column(name = "cod_prueba_seleccion")
	private Integer cod_prueba_seleccion;
	@Column(name = "cod_datos_personales")
	private Integer cod_datos_personales;
	@Column(name = "cod_documento")
	private Integer cod_documento;
	@Column(name = "cod_prueba")
	private Integer cod_prueba;
	@Column(name = "mensaje")
	private String mensaje;
	@Column(name = "instrucciones")
	private String instrucciones;
	@Column(name = "estado")
	private String estado;
	@Column(name = "fecha_prueba")
	@JsonFormat(pattern = "yyyy-MM-dd ")
	private Date fecha_prueba;
	
	
	
}
