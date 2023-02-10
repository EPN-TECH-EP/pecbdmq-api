package epntech.cbdmq.pe.constante;

public class PermisosConst {
    public static final String[] USUARIO_PERMISOS = { "user:read" };
    public static final String[] HR_PERMISOS = { "user:read", "user:update" };
    public static final String[] JEFE_PERMISOS = { "user:read", "user:update" };
    public static final String[] ADMIN_PERMISOS = { "user:read", "user:create", "user:update" };
    public static final String[] SUPER_ADMIN_PERMISOS = { "user:read", "user:create", "user:update", "user:delete" };
}
