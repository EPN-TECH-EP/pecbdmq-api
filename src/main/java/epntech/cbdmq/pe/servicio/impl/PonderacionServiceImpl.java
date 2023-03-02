package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.dominio.admin.Ponderacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import epntech.cbdmq.pe.repositorio.admin.PonderacionRepository;
import epntech.cbdmq.pe.servicio.PonderacionService;

@Service
public class PonderacionServiceImpl implements PonderacionService {

	@Autowired
	private PonderacionRepository repo;
	
	@Override
	public Ponderacion save(Ponderacion obj) throws DataException {
		if (obj.getTiponotaponderacion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		// Optional<EspCurso> objGuardado =
		// repo.findBynombrecursoespecializacion(obj.getNombrecursoespecializacion());
		Optional<Ponderacion> objGuardado = repo.findByTiponotaponderacion(obj.getTiponotaponderacion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<Ponderacion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Ponderacion> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Ponderacion update(Ponderacion objActualizado) throws DataException {
		if (objActualizado.getTiponotaponderacion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Ponderacion> objGuardado = repo.findByTiponotaponderacion(objActualizado.getTiponotaponderacion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

}
