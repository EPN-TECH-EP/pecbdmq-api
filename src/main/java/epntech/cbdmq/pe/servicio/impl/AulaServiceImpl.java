package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.servicio.AulaService;
import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class AulaServiceImpl implements AulaService {

	@Autowired
	private AulaRepository repo;

	@Override
	public Aula save(Aula obj) throws DataException {
		if (obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Aula> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			Aula aula = objGuardado.get();
			
			// si requiere crear un registro que ya está presente con borrado lógico, lo reactiva
			if (aula.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				aula.setEstado(EstadosConst.ACTIVO);
				return repo.save(aula);
			} else {
				// si ya existe y no está eliminado
				throw new DataException(REGISTRO_YA_EXISTE);
			}
			
		}

		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
	}

	@Override
	public List<Aula> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Aula> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Aula update(Aula objActualizado) throws DataException {
	if(objActualizado.getNombre() !=null) {
		Optional<Aula> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		if (objGuardado.isPresent()&& !objGuardado.get().getCodigo().equals(objActualizado.getCodigo())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
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
