package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EspTipoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EspTipoCursoRepository;
import epntech.cbdmq.pe.servicio.EspTipoCursoService;

@Service
public class EspTipoCursoServiceImpl implements EspTipoCursoService{

	
	@Autowired
	private EspTipoCursoRepository espTipoCursoRepository;
	
	
	@Override
	public EspTipoCurso save(EspTipoCurso obj) throws DataException {
		
		return espTipoCursoRepository.save(obj);
	}

	@Override
	public List<EspTipoCurso> getAll() {
		// TODO Auto-generated method stub
		return espTipoCursoRepository.findAll();
	}

	@Override
	public Optional<EspTipoCurso> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return espTipoCursoRepository.findById(codigo);
	}

	@Override
	public EspTipoCurso update(EspTipoCurso objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return espTipoCursoRepository.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) throws DataException {
		// TODO Auto-generated method stub
		espTipoCursoRepository.deleteById(codigo);
	}

	
	
	
}
