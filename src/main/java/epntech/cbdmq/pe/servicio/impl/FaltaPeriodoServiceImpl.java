package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.TipoFaltaPeriodoUtil;
import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.FaltaPeriodo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.FaltaPeriodoRepository;
import epntech.cbdmq.pe.servicio.FaltaPeriodoService;

@Service
public class FaltaPeriodoServiceImpl implements FaltaPeriodoService{

	@Autowired
	private FaltaPeriodoRepository faltaPeriodoRepository;
	@Autowired
	private PeriodoAcademicoService periodoAcademicoService;
	
	@Override
	public FaltaPeriodo save(FaltaPeriodo obj) throws DataException {
		// TODO Auto-generated method stub
		return faltaPeriodoRepository.save(obj);
	}

	@Override
	public List<FaltaPeriodo> getAll() {
		// TODO Auto-generated method stub
		return faltaPeriodoRepository.findAll();
	}

	@Override
	public Optional<FaltaPeriodo> getById(int id) {
		// TODO Auto-generated method stub
		return faltaPeriodoRepository.findById(id);
	}

	@Override
	public FaltaPeriodo update(FaltaPeriodo objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return faltaPeriodoRepository.save(objActualizado);
	}


	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = faltaPeriodoRepository.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			faltaPeriodoRepository.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
		
	}

	@Override
	public List<TipoFaltaPeriodoUtil> getFaltasPeriodo() {
			return faltaPeriodoRepository.getFaltasPeriodo(periodoAcademicoService.getPAActivo());
	}


}
