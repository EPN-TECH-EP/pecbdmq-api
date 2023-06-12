package epntech.cbdmq.pe.dominio.util;

import java.util.Date;

import epntech.cbdmq.pe.dominio.Usuario;
import lombok.Data;

@Data
public class UsuarioDtoRead{

    private static final long serialVersionUID = -4289535118198584986L;

    private Long codUsuario;
    private Long codModulo;
    private String nombreUsuario;
    private String clave;
    private Date fechaUltimoLogin;
    private Date fechaUltimoLoginMostrar;
    private Date fechaRegistro;
    private boolean isActive;
    private boolean isNotLocked;
    private String nombre;
    private String apellido;
    private String correoPersonal;

    public UsuarioDtoRead() {
    }

    public UsuarioDtoRead(Long codUsuario, Long codModulo, String nombreUsuario, String clave, Date fechaUltimoLogin, Date fechaUltimoLoginMostrar, Date fechaRegistro, boolean isActive, boolean isNotLocked, Long cod_usuario, String nombre, String apellido, String correoPersonal) {
        super();
        this.codUsuario = cod_usuario;
        this.codModulo = codModulo;
        this.nombreUsuario =nombreUsuario;
        this.clave=clave;
        this.fechaUltimoLogin =fechaUltimoLogin;
        this.fechaUltimoLoginMostrar =fechaUltimoLoginMostrar;
        this.fechaRegistro =fechaRegistro;
        this.isActive =isActive;
        this.isNotLocked =isNotLocked;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correoPersonal = correoPersonal;
    }

}
