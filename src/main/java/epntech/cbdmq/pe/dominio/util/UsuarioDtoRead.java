package epntech.cbdmq.pe.dominio.util;

import java.util.Date;

import epntech.cbdmq.pe.dominio.Usuario;
import lombok.Data;

@Data
public class UsuarioDtoRead{

    private static final long serialVersionUID = -4289535118198584986L;

    private Long cod_usuario;
    private Long cod_modulo;
    private String nombre_usuario;
    private String clave;
    private Date fecha_ultimo_login;
    private Date fecha_ultimo_login_mostrar;
    private Date fecha_registro;
    private boolean is_active;
    private boolean is_not_locked;
    private String nombre;
    private String apellido;
    private String correo_personal;

    public UsuarioDtoRead() {
    }

    public UsuarioDtoRead(Long codUsuario, Long codModulo, String nombreUsuario, String clave, Date fechaUltimoLogin, Date fechaUltimoLoginMostrar, Date fechaRegistro, boolean isActive, boolean isNotLocked, Long cod_usuario, String nombre, String apellido, String correo_personal) {
        super();
        this.cod_usuario = cod_usuario;
        this.cod_modulo = codModulo;
        this.nombre_usuario=nombreUsuario;
        this.clave=clave;
        this.fecha_ultimo_login=fechaUltimoLogin;
        this.fecha_ultimo_login_mostrar=fechaUltimoLoginMostrar;
        this.fecha_registro=fechaRegistro;
        this.is_active=isActive;
        this.is_not_locked=isNotLocked;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo_personal=correo_personal;
    }

}
