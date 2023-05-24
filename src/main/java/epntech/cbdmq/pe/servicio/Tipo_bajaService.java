package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dto.Tipo_BajaDTO;

public interface Tipo_bajaService {
	
	public List<Tipo_BajaDTO>findByBaja(String baja);

}
