package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.DocumentoHabilitante;
import epntech.cbdmq.pe.repositorio.admin.DocumentoHabilitanteRepository;
import epntech.cbdmq.pe.servicio.DocumentoHabilitanteService;

@Service
public class DocumentoHabilitanteServiceImpl implements DocumentoHabilitanteService {

	@Autowired
	private DocumentoHabilitanteRepository repo;

	@Override
	public DocumentoHabilitante save(DocumentoHabilitante obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<DocumentoHabilitante> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<DocumentoHabilitante> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public DocumentoHabilitante update(DocumentoHabilitante objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}
	
	
}