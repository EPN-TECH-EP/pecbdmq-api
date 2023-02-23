package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;
import epntech.cbdmq.pe.servicio.ConvocatoriaService;

@Service
public class ConvocatoriaServicieImpl implements ConvocatoriaService{

	@Autowired
	private ConvocatoriaRepository repo;
	
	@Override
	public Convocatoria saveData(Convocatoria obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<Convocatoria> getAllData() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Convocatoria> getByIdData(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Convocatoria updateData(Convocatoria objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void deleteData(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
