package epntech.cbdmq.pe.dominio.admin.especializacion;

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
	
	@OneToMany
	@JoinColumn(name = "cod_inscripcion")
	private Set<InscripcionDocumentoDatos> documentos;
}

