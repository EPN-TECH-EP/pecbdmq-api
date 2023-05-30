package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;

import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.servicio.PruebaDetalleService;

@Service
public class PruebaDetalleServiceImpl implements PruebaDetalleService{

	@Autowired
	private PruebaDetalleRepository pruebaDetalleRepository;
	
	@Override
	public Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subtipo, periodo);
	}
	@Override
	public PruebaDetalle save(PruebaDetalle obj)  {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.save(obj);
	}

	@Override
	public List<PruebaDetalle> getAll() {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.findAll();
	}

	@Override
	public Optional<PruebaDetalle> getById(int id) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.findById(id);
	}

	@Override
	public PruebaDetalle update(PruebaDetalle objActualizado) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = pruebaDetalleRepository.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			pruebaDetalleRepository.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
	}

	}
}
