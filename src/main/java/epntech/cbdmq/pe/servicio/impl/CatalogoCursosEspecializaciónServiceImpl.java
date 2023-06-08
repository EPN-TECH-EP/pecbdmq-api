package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.CatalogoCursosEspecialización;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursosEspecializaciónRepository;
import epntech.cbdmq.pe.servicio.CatalogoCursosEspecializaciónService;


@Service
public class CatalogoCursosEspecializaciónServiceImpl implements CatalogoCursosEspecializaciónService{

	@Autowired
	private CatalogoCursosEspecializaciónRepository catalogoCursosEspecializaciónRepository;
	
	@Override
	public CatalogoCursosEspecialización save(CatalogoCursosEspecialización obj) throws DataException {
		
		return catalogoCursosEspecializaciónRepository.save(obj);
	}

	@Override
	public List<CatalogoCursosEspecialización> getAll() {
		// TODO Auto-generated method stub
		return catalogoCursosEspecializaciónRepository.findAll();
	}

	@Override
	public Optional<CatalogoCursosEspecialización> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return catalogoCursosEspecializaciónRepository.findById(codigo);
	}

	@Override
	public CatalogoCursosEspecialización update(CatalogoCursosEspecialización objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return catalogoCursosEspecializaciónRepository.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		

		 catalogoCursosEspecializaciónRepository.deleteById(codigo);
		
	}

}
