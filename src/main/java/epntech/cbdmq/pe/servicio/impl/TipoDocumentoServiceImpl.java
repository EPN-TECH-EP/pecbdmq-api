package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoDocumento;
import epntech.cbdmq.pe.repositorio.admin.TipoDocumentoRepository;
import epntech.cbdmq.pe.servicio.TipoDocumentoService;


@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	@Autowired
	private TipoDocumentoRepository repo;
	
	@Override
	public TipoDocumento save(TipoDocumento obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<TipoDocumento> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<TipoDocumento> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public TipoDocumento update(TipoDocumento objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
