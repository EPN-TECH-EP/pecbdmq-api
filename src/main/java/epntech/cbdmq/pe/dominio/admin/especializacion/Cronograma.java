package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;

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
@Entity
@Table(name = "esp_cronograma")
@SQLDelete(sql = "UPDATE {h-schema}esp_cronograma SET estado = 'ELIMINADO' WHERE cod_cronograma = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Cronograma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_cronograma")
	private Long codCronograma;
	
	@Column(name = "cod_documento")
	private Long codDocumento;
	
	@Column(name = "fecha_inicio")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicio;
	
	@Column(name = "fecha_fin")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFin;
	
	@Column(name = "estado")
	private String estado;

}
