package epntech.cbdmq.pe.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ValidacionRequisitos;
import epntech.cbdmq.pe.repositorio.admin.PostulanteRepository;
import epntech.cbdmq.pe.repositorio.admin.ValidacionRequisitosForRepository;
import epntech.cbdmq.pe.servicio.ValidacionRequisitosForService;

@Service
public class ValidacionRequisitosForServiceImpl implements ValidacionRequisitosForService {

	@Autowired
	private ValidacionRequisitosForRepository repo;

	@Autowired
	private PostulanteRepository postulanteRepo;


	@Override
	public List<ValidacionRequisitos> update(List<ValidacionRequisitos> objActualizado) {
		List<ValidacionRequisitos> lista = new ArrayList<>();
		lista = repo.saveAll(objActualizado);
		if(!lista.isEmpty())
			postulanteRepo.updateState(objActualizado.get(0).getCodPostulante());
		return lista;
	}

}
