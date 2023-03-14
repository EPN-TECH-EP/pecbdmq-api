package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;

@Service
public class DocumentoServiceimpl implements DocumentoService {

	@Autowired
	private DocumentoRepository repo;

	@Override
	public Documento save(Documento obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<Documento> listAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Documento> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Documento update(Documento objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}
	
}
