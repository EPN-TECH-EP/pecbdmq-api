package epntech.cbdmq.pe.dominio.admin.especializacion;

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
@Table(name = "esp_modulo")
@SQLDelete(sql = "UPDATE {h-schema}esp_modulo SET estado = 'ELIMINADO' WHERE cod_esp_modulo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ModuloEspecializacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_esp_modulo")
	private Long codEspModulo;
	
	@Column(name = "nombre_esp_modulo")
	private String nombreEspModulo;
	
	@Column(name = "estado")
	private String estado;

}
