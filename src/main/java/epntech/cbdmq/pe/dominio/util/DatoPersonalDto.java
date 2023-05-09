package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DatoPersonalDto {
    private String cedula;
    private String nombre;
    private String apellido;
    private Date fecha_nacimiento;
    private String tipo_sangre;
    private String tipo_nacionalidad;
    private String correo_personal;
    private String correo_institucional;
    private String provincia_nacimiento;
    private String canton_nacimiento;
    private String provincia_residencia;
    private String canton_residencia;
    private String num_telef_convencional;
    private String num_telef_celular;
    private String calle_principal_residencia;
    private String calle_secundaria_residencia;
    private String numero_casa;
    private String colegio;
    private String nombre_titulo;
    private String pais_titulo;
    private String ciudad_titulo;
    private Boolean tiene_merito_deportivo;
    private Boolean tiene_merito_academico;
    private String merito_deportivo_descripcion;
    private String merito_academico_descripcion;
    private String cargo;
    private String rango;
    private String grado;

    public DatoPersonalDto() {
    }

    public DatoPersonalDto(String cedula, String nombre, String apellido, Date fecha_nacimiento, String tipo_sangre, String tipo_nacionalidad, String correo_personal, String correo_institucional, String provincia_nacimiento, String canton_nacimiento, String provincia_residencia, String canton_residencia, String num_telef_convencional, String num_telef_celular, String calle_principal_residencia, String calle_secundaria_residencia, String numero_casa, String colegio, String nombre_titulo, String pais_titulo, String ciudad_titulo, Boolean tiene_merito_deportivo, Boolean tiene_merito_academico, String merito_deportivo_descripcion, String merito_academico_descripcion, String cargo, String rango, String grado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.tipo_sangre = tipo_sangre;
        this.tipo_nacionalidad = tipo_nacionalidad;
        this.correo_personal = correo_personal;
        this.correo_institucional = correo_institucional;
        this.provincia_nacimiento = provincia_nacimiento;
        this.canton_nacimiento = canton_nacimiento;
        this.provincia_residencia = provincia_residencia;
        this.canton_residencia = canton_residencia;
        this.num_telef_convencional = num_telef_convencional;
        this.num_telef_celular = num_telef_celular;
        this.calle_principal_residencia = calle_principal_residencia;
        this.calle_secundaria_residencia = calle_secundaria_residencia;
        this.numero_casa = numero_casa;
        this.colegio = colegio;
        this.nombre_titulo = nombre_titulo;
        this.pais_titulo = pais_titulo;
        this.ciudad_titulo = ciudad_titulo;
        this.tiene_merito_deportivo = tiene_merito_deportivo;
        this.tiene_merito_academico = tiene_merito_academico;
        this.merito_deportivo_descripcion = merito_deportivo_descripcion;
        this.merito_academico_descripcion = merito_academico_descripcion;
        this.cargo = cargo;
        this.rango = rango;
        this.grado = grado;
    }
}
