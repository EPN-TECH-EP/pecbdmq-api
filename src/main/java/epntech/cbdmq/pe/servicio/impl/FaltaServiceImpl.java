package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Falta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.FaltaRepository;
import epntech.cbdmq.pe.servicio.FaltaService;


@Service
public class FaltaServiceImpl implements FaltaService{


    @Autowired
	private FaltaRepository faltaRepository;
	
	@Override
	public Falta save(Falta obj) throws DataException {

		return faltaRepository.save(obj);

	}	

	@Override
	public List<Falta> getAll() {
		// TODO Auto-generated method stub

		return faltaRepository.findAll();

	}

	@Override
	public Optional<Falta> getById(int id) {
		// TODO Auto-generated method stub

		return faltaRepository.findById(id);

	}

	@Override
	public Falta update(Falta objActualizado) throws DataException {

		return faltaRepository.save(objActualizado);

	}

	@Override
	public void delete(int id) throws DataException {

		Optional<?> objGuardado = faltaRepository.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			faltaRepository.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}

		
	}

}

