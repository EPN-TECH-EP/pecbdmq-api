package epntech.cbdmq.pe.dominio.admin;


import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

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
	private Integer codNotifica;
	@Column(name = "cod_prueba_seleccion")
	private Integer codPruebaSeleccion;
	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	@Column(name = "cod_documento")
	private Integer codDocumento;
	@Column(name = "cod_prueba")
	private Integer codPrueba;
	@Column(name = "mensaje")
	private String mensaje;
	@Column(name = "instrucciones")
	private String instrucciones;
	@Column(name = "estado")
	private String estado;
	@Column(name = "fecha_notificacion")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaPrueba;
	@Column(name = "hora")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime  hora;
	
	
	
	
}
