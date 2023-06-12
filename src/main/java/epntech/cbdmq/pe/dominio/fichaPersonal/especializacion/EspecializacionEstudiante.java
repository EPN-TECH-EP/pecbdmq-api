package epntech.cbdmq.pe.dominio.fichaPersonal.especializacion;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;
@Data
public class EspecializacionEstudiante {
    private String cedula;
    private String nombre;
    private String apellido;
    private String codigoUnicoEstudiante;
    private String nombreCargo;
    private String nombreRango;
    private String nombreGrado;
    private String nombreTipoInstructor;
    private String instructor;
    private String nombreAula;
    private String nombreTipo;
    private String nombreCatalogo;
    private Date fechaInicioCurso;
    private Date fechaFinCurso;
    private Date fechaInicioCarga;
    private Date fechaFinCarga;
    private Date fechaCreaNota;
    private Time horaCreaNota;
    private String usuarioModificacion;
    private Date fechaModNota;
    private Time horaModNota;
    private BigDecimal notaFinal;
    private boolean resultado;

    public EspecializacionEstudiante(String cedula, String nombre, String apellido, String codigoUnicoEstudiante, String nombreCargo, String nombreRango, String nombreGrado, String nombreTipoInstructor, String instructor, String nombreAula, String nombreTipo, String nombreCatalogo, Date fechaInicioCurso, Date fechaFinCurso, Date fechaInicioCarga, Date fechaFinCarga, Date fechaCreaNota, Time horaCreaNota, String usuarioModificacion, Date fechaModNota, Time horaModNota, BigDecimal notaFinal, boolean resultado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigoUnicoEstudiante = codigoUnicoEstudiante;
        this.nombreCargo = nombreCargo;
        this.nombreRango = nombreRango;
        this.nombreGrado = nombreGrado;
        this.nombreTipoInstructor = nombreTipoInstructor;
        this.instructor = instructor;
        this.nombreAula = nombreAula;
        this.nombreTipo = nombreTipo;
        this.nombreCatalogo = nombreCatalogo;
        this.fechaInicioCurso = fechaInicioCurso;
        this.fechaFinCurso = fechaFinCurso;
        this.fechaInicioCarga = fechaInicioCarga;
        this.fechaFinCarga = fechaFinCarga;
        this.fechaCreaNota = fechaCreaNota;
        this.horaCreaNota = horaCreaNota;
        this.usuarioModificacion = usuarioModificacion;
        this.fechaModNota = fechaModNota;
        this.horaModNota = horaModNota;
        this.notaFinal = notaFinal;
        this.resultado = resultado;
    }
}
