package epntech.cbdmq.pe.dominio.util;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.DocumentoPostulante;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class PostulanteDatos {

	@Id
	private Integer codigo;	
	private String idPostulante;
	private Date fechaPostulacion;
	private Integer edadPostulacion;
	private Integer codDatosPersonales;
	private String apellido;
	private String cedula;
	private String nombre;
	private Date fechaNacimiento;
	private String tipoSangre;
	private String sexo;
	private String cantonNacimiento;
	private String cantonResidencia;
	private String callePrincipalResidencia;
	private String calleSecundariaResidencia;
	private String numeroCasa;
	private String colegio;
	private String tipoNacionalidad;
	private String numTelefCelular;
	private String nombreTituloSegundoNivel;
	private String paisTituloSegundoNivel;
	private String ciudadTituloSegundoNivel;
	private String meritoAcademicoDescripcion;
	private String meritoDeportivoDescripcion;
	private String provinciaNacimiento;
	private String provinciaResidencia;
	private String correoPersonal;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "gen_postulante_documento", joinColumns = @JoinColumn(name = "cod_postulante"), inverseJoinColumns = @JoinColumn(name = "cod_documento"))
	private Set<DocumentoPostulante> documentos = new HashSet<>();
	
}
