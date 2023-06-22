package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.math.BigDecimal;

import org.hibernate.annotations.NamedNativeQuery;

import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso_modulo")

@NamedNativeQuery(name = "CursoModulo.findCursoModulo", 
query = "select cm.cod_curso_modulo as codCursoModelo, m.cod_esp_modulo as codEspModulo, m.nombre_esp_modulo as nombreEspModulo, "
		+ "c.cod_curso_especializacion as codCursoEspecializacion, cc.nombre_catalogo_curso as nombreCatalogoCurso, cm.porcentaje "
		+ "from cbdmq.esp_curso_modulo cm, cbdmq.esp_modulo m, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
		+ "where cm.cod_esp_modulo  = m.cod_esp_modulo "
		+ "and cm.cod_curso_especializacion = c.cod_curso_especializacion "
		+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
		+ "and upper(m.estado) = 'ACTIVO' "
		+ "and upper(c.estado) = 'ACTIVO' "
		+ "and upper(cc.estado) = 'ACTIVO' ", 
		resultSetMapping = "findCursoModulo")
@SqlResultSetMapping(name = "findCursoModulo", classes = @ConstructorResult(targetClass = CursoModuloDatosEspecializacion.class, columns = {
		@ColumnResult(name = "codCursoModelo"), 
		@ColumnResult(name = "codEspModulo"), 
		@ColumnResult(name = "nombreEspModulo"),
		@ColumnResult(name = "codCursoEspecializacion"), 
		@ColumnResult(name = "nombreCatalogoCurso"), 
		@ColumnResult(name = "porcentaje"),}))

public class CursoModulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_modulo")
	private Long codCursoModulo;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "cod_esp_modulo")
	private Long codEspModulo;
	
	@Column(name = "porcentaje")
	private BigDecimal porcentaje;
	
}
