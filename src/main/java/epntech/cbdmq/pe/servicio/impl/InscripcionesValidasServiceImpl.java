package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import epntech.cbdmq.pe.dominio.admin.InscripcionesValidas;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.admin.InscripcionesValidasRepository;
import epntech.cbdmq.pe.servicio.InscripcionesValidasService;

public class InscripcionesValidasServiceImpl implements InscripcionesValidasService{

	@Autowired
	
	
	private InscripcionesValidasRepository repo;
	
	
	
	@Override
	public List<InscripcionesValidasUtil> getinscripcioneslistar() {
		// TODO Auto-generated method stub
		return repo.getinscripcioneslistar();
	}

}
