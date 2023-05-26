package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.admin.CatalogoRespuesta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.CatalogoRespuestaRepository;
import epntech.cbdmq.pe.servicio.CatalogoRespuestaService;
@Service
public class CatalogoRespuestaServiceImpl implements CatalogoRespuestaService{
 
	@Autowired
	private CatalogoRespuestaRepository repo;
	
	@Override
	public CatalogoRespuesta save(CatalogoRespuesta obj) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<CatalogoRespuesta> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<CatalogoRespuesta> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public CatalogoRespuesta update(CatalogoRespuesta objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

}
