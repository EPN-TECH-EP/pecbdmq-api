package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EncuestaPreguntaRespuesta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EncuestaPreguntaRespuestaRepository;
import epntech.cbdmq.pe.servicio.EncuestaPreguntaRespuestaService;

@Service
public class EncuestaPreguntaRespuestaServiceImpl implements EncuestaPreguntaRespuestaService{

	@Autowired
	private EncuestaPreguntaRespuestaRepository encuestaFormularioRepository;
	
	
	@Override
	public EncuestaPreguntaRespuesta save(EncuestaPreguntaRespuesta obj) throws DataException {
		EncuestaPreguntaRespuesta enform = encuestaFormularioRepository.save(obj);
		
		return enform;
	}

	@Override
	public List<EncuestaPreguntaRespuesta> getAll() {
		// TODO Auto-generated method stub
		return encuestaFormularioRepository.findAll();
	}




}
