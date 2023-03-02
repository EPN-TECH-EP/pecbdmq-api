package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.PruebaFisica;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PruebaFisicaRepository;
import epntech.cbdmq.pe.servicio.PruebaFisicaService;
@Service
public class PruebaFisicaServiceImpl  implements PruebaFisicaService{

	@Autowired
	private PruebaFisicaRepository repo;

	@Override
	public PruebaFisica save(PruebaFisica obj) throws DataException {
		
		return repo.save(obj);
	}

	@Override
	public List<PruebaFisica> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<PruebaFisica> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public PruebaFisica update(PruebaFisica objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}
}
