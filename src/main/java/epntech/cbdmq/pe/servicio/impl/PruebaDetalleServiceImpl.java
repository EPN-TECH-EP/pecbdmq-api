package epntech.cbdmq.pe.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.servicio.PruebaDetalleService;

@Service
public class PruebaDetalleServiceImpl implements PruebaDetalleService {

	@Autowired
	private PruebaDetalleRepository pruebaDetalleRepository;
	
	@Override
	public Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subtipo, periodo);
	}

	@Override
	public PruebaDetalle update(PruebaDetalle objActualizado) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.save(objActualizado);
	}

	@Override
	public PruebaDetalle save(PruebaDetalle obj) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.save(obj);
	}

}
