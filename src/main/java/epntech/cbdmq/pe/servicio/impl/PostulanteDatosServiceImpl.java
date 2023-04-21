package epntech.cbdmq.pe.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import epntech.cbdmq.pe.dominio.admin.PostulanteDatos;
import epntech.cbdmq.pe.repositorio.admin.PostulanteDatosRepository;
import epntech.cbdmq.pe.servicio.PostulanteDatosService;


@Service
public class PostulanteDatosServiceImpl implements PostulanteDatosService {

	@Autowired
	private PostulanteDatosRepository repo;

	@Override
	public Optional<PostulanteDatos> getDatos(Integer codPostulante) {
		// TODO Auto-generated method stub
		return repo.getDatos(codPostulante);
	}
	
}
