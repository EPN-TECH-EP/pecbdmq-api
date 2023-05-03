package epntech.cbdmq.pe.servicio;

import java.util.List;


import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;
import epntech.cbdmq.pe.dominio.admin.InscripcionesValidas;

import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;

public interface InscripcionesValidasService {

	List<InscripcionesValidasUtil> getinscripcioneslistar();
	
}
