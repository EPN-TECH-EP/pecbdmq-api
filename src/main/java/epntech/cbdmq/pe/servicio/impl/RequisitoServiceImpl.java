package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.RequisitoRepository;
import epntech.cbdmq.pe.servicio.RequisitoService;

@Service
public class RequisitoServiceImpl implements RequisitoService {

	@Autowired
	private RequisitoRepository repo;
	
	@Override
	public Requisito save(Requisito obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Requisito> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			Requisito stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
			throw new DataException(REGISTRO_YA_EXISTE);
			}

		}
		return repo.save(obj);
	}

	@Override
	public List<Requisito> getAll() {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Sort.Direction.ASC, "nombre");
		return repo.findAll(sort);
	}

	@Override
	public List<Requisito> getAllOrder() {
		return null;
	}

	@Override
	public Optional<Requisito> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Requisito update(Requisito objActualizado) throws DataException {
		Optional<Requisito> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		if (objGuardado.isPresent()&& !objGuardado.get().getCodigoRequisito().equals(objActualizado.getCodigoRequisito())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}

}
