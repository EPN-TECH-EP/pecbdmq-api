package epntech.cbdmq.pe.dominio.admin.especializacion;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso_prueba")
public class CursoPrueba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_prueba_ingreso")
	private Long codCursoPruebaIngreso;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "cod_prueba_detalle")
	private Long codPruebaDetalle;
}