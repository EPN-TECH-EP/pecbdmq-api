package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.TipoInstruccion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoInstruccionRepository;
import epntech.cbdmq.pe.servicio.TipoInstruccionService;


@Service
public class TipoInstruccionServiceImpl implements TipoInstruccionService {

	@Autowired
	private TipoInstruccionRepository repo;
	
	@Override
	public TipoInstruccion save(TipoInstruccion obj) throws DataException {
		if(obj.getTipoInstruccion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoInstruccion> objGuardado = repo.findByTipoInstruccionIgnoreCase(obj.getTipoInstruccion());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			TipoInstruccion stp = objGuardado.get();
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
	public List<TipoInstruccion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<TipoInstruccion> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public TipoInstruccion update(TipoInstruccion objActualizado) throws DataException {
		Optional<TipoInstruccion> objGuardado = repo.findByTipoInstruccionIgnoreCase(objActualizado.getTipoInstruccion());
		if (objGuardado.isPresent()&& !objGuardado.get().getCodigoTipoInstruccion().equals(objActualizado.getCodigoTipoInstruccion())) {
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
