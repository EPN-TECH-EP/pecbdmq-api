package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoFuncionario;
import epntech.cbdmq.pe.dominio.admin.UnidadGestion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.UnidadGestionRepository;
import epntech.cbdmq.pe.servicio.UnidadGestionService;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class UnidadGestionServiceImpl implements UnidadGestionService {

	@Autowired
	private UnidadGestionRepository repo;

	@Override
	public UnidadGestion saveUnidadGestion(UnidadGestion obj) throws DataException {
		if (obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<UnidadGestion> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(obj);
	}

	@Override
	public List<UnidadGestion> getAllUnidadGestion() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<UnidadGestion> getUnidadGestionById(int codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public UnidadGestion updateUnidadGestion(UnidadGestion objActualizado) throws DataException {

		Optional<UnidadGestion> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		if (objGuardado.isPresent() && !objGuardado.get().getCodigo().equals(objActualizado.getCodigo())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(objActualizado);
	}

	@Override
	public void deleteUnidadGestion(int id) throws DataException {
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
