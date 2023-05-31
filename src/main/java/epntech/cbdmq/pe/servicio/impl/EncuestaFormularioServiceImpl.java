package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EncuestaFormulario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EncuestaFormularioRepository;
import epntech.cbdmq.pe.servicio.EncuestaFormularioService;

@Service
public class EncuestaFormularioServiceImpl implements EncuestaFormularioService{

	@Autowired
	private EncuestaFormularioRepository encuestaFormularioRepository;
	
	
	@Override
	public EncuestaFormulario save(EncuestaFormulario obj) throws DataException {
		EncuestaFormulario enform = encuestaFormularioRepository.save(obj);
		
		return enform;
	}

	@Override
	public List<EncuestaFormulario> getAll(Integer codEncuesta) {
		// TODO Auto-generated method stub
		return encuestaFormularioRepository.encuesta(codEncuesta);
	}




}
