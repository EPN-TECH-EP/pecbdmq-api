package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EncuestaPregunta;
import epntech.cbdmq.pe.repositorio.admin.EncuestaPreguntaRepository;
import epntech.cbdmq.pe.servicio.EncuestaPreguntaService;

@Service
public class EncuestaPreguntaServiceImpl implements EncuestaPreguntaService {

	@Autowired
	private EncuestaPreguntaRepository encuestaPreguntaRepository;
	
	@Override
	public List<EncuestaPregunta> getAll() {
		// TODO Auto-generated method stub
		return encuestaPreguntaRepository.findAll();
	}

	
	
	
	
}
