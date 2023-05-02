package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosLista;
import epntech.cbdmq.pe.repositorio.admin.ValidacionRequisitosRepository;
import epntech.cbdmq.pe.servicio.ValidacionRequisitosService;

@Service
public class ValidacionRequisitosServiceImpl implements ValidacionRequisitosService {
	
	@Autowired
	private ValidacionRequisitosRepository repo;

	@Override
	public List<ValidacionRequisitosLista> getRequisitos(Integer codPostulante) {
		// TODO Auto-generated method stub
		return repo.getRequisitos(codPostulante);
	}

}
