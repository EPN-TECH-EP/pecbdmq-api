package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ParametrizaPruebaDetalle;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.repositorio.admin.ParametrizaPruebaDetalleRepository;
import epntech.cbdmq.pe.servicio.ParametrizaPruebaDetalleService;

@Service
public class ParametrizaPruebaDetalleServiceImpl implements ParametrizaPruebaDetalleService{

	
	@Autowired
	private ParametrizaPruebaDetalleRepository repo;
	
	@Override
	public ParametrizaPruebaDetalle save(ParametrizaPruebaDetalle obj) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<ParametrizaPruebaDetalle> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<ParametrizaPruebaDetalle> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public ParametrizaPruebaDetalle update(ParametrizaPruebaDetalle objActualizado) throws DataException {
		// TODO Auto-generated method stub
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
	
	
	// buscar lista por parametrizaPruebaResumen
	@Override
	public List<ParametrizaPruebaDetalle> listarPorResumen(Integer codResumen) {
		return repo.findAllByCodParametrizaPruebaResumenOrderByEdadInicioMesesAndSexo(codResumen);
	} 

}
