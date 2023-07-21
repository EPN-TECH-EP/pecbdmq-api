package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class InscripcionDatosEsp {

	@Id
	private Long codInscripcion;
	private String cedula;
	private String nombre;
	private String apellido;
	private String nombreCatalogoCurso;
	private LocalDate fechaInscripcion;
	private LocalDate fechaInicioCurso;
	private LocalDate fechaFinCurso;
	
	@OneToMany
	@JoinColumn(name = "cod_inscripcion")
	private Set<InscripcionDocumentoDatos> documentos;
}

