package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.DocumentoPostulante;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class ProInscripcionDto {
    @Id
    private Integer codInscripcion;
    private Integer codEstudiante;
    private Integer codConvocatoria;
    private Date fechaInscripcion;
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
    private Boolean aceptado;
    private Boolean convalidacion;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pro_inscripcion_documento",
            joinColumns = @JoinColumn(name = "cod_inscripcion"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
    private Set<DocumentoPostulante> documentos = new HashSet<>();
}
