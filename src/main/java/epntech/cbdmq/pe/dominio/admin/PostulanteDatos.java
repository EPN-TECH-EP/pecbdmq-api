package epntech.cbdmq.pe.dominio.admin;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PostulanteDatos {

	@Id
	private Integer codPostulante;	
	private String idPostulante;
	private Date fechaPostulacion;
	private Integer edadPostulacion;
	private Integer codDatosPersonales;
	private String apellido;
	private String cedula;
	private String nombre;
	private Date fechaNacimiento;
	private String tipoSangre;
	private String genero;
	private String cantonNacimiento;
	private String cantonResidencia;
	private String callePrincipalResidencia;
	private String calleSecundariaResidencia;
	private String numeroCasa;
	private String colegio;
	private String tipoNacionalidad;
	private String numTelefCelular;
	private String nombreTitulo;
	private String paisTitulo;
	private String ciudadTitulo;
	private String meritoAcademicoDescripcion;
	private String meritoDeportivoDescripcion;
	private String provinciaNacimiento;
	private String provinciaResidencia;
	
}
