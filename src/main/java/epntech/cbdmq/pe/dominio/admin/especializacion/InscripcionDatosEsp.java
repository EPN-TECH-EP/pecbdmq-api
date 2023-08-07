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
	private String callePrincipalResidencia;
	private String calleSecundariaResidencia;
	private String cantonNacimiento;
	private String cantonResidencia;
	private String ciudadTituloSegundoNivel;
	private Integer codDatosPersonales;
	private String colegio;
	private String meritoAcademicoDescripcion;
	private String meritoDeportivoDescripcion;
	private String nombreTituloSegundoNivel;
	private String numTelefCelular;
	private String numeroCasa;
	private String paisTituloSegundoNivel;
	private String provinciaNacimiento;
	private String provinciaResidencia;
	private String sexo;
	private String tipoNacionalidad;
	private String tipoSangre;
	private LocalDate fechaInscripcion;
	private LocalDate fechaInicioCurso;
	private LocalDate fechaFinCurso;
	private LocalDate fechaNacimiento;
	
	@OneToMany
	@JoinColumn(name = "cod_inscripcion")
	private Set<InscripcionDocumentoDatos> documentos;
}

