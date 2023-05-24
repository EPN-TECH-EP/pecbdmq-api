package epntech.cbdmq.pe.constante;

public class Tablas {
	
	//Estados
	/*public static final String ESTADO_ACTIVO = "ACTIVO";
	public static final String ESTADO_ELIMINADO = "ELIMINADO";*/
	
	
	// =============================================================================================
		// NOMBRE DE LAS TABLAS
		// =============================================================================================
	public static final String TAB_NOMBRE_TIPO_BAJA = "gen_tipo_baja";
	
	
	// =============================================================================================
	// NOMBRE DE LOS ESQUEMAS
	// =============================================================================================
	public static final String SEC_NOMBRE_DBO = "cbdmq";
	
	// =============================================================================================
		// NOMBRE DE LOS PROCEDIMIENTOS ALMACENADOS
		// =============================================================================================
	public static final String SP_BUSCAR_TIPO_BAJA = "select *from cbdmq.fn_tipo_baja_activos('','');";
	
	
	
	
}
