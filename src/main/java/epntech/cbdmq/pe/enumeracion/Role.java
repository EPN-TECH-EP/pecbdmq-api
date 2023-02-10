package epntech.cbdmq.pe.enumeracion;

import static epntech.cbdmq.pe.constante.PermisosConst.*;

public enum Role {
    ROL_USUARIO(USUARIO_PERMISOS),
    ROL_HR(HR_PERMISOS),
    ROL_JEFE(JEFE_PERMISOS),
    ROL_ADMIN(ADMIN_PERMISOS),
    ROL_SUPER_ADMIN(SUPER_ADMIN_PERMISOS);

    private String[] permisos;

    Role(String... permisos) {
        this.permisos = permisos;
    }

    public String[] getPermisos() {
        return permisos;
    }
}
