package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EncuestaResultado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EncuestaResultadoRepository;
import epntech.cbdmq.pe.servicio.EncuestaResultadoService;

@Service
public class EncuestaResultadoServiceImpl implements EncuestaResultadoService{

	@Autowired
	private EncuestaResultadoRepository encuestaResultadoRepository;
	
	@Override
	public EncuestaResultado save(EncuestaResultado obj) throws DataException {
		// TODO Auto-generated method stub
		return encuestaResultadoRepository.save(obj);
	}

	@Override
	public List<EncuestaResultado> getAll() {
		// TODO Auto-generated method stub
		return encuestaResultadoRepository.findAll();
	}

	@Override
	public Optional<EncuestaResultado> getById(int id) {
		// TODO Auto-generated method stub
		return encuestaResultadoRepository.findById(id);
	}

	@Override
	public EncuestaResultado update(EncuestaResultado objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return encuestaResultadoRepository.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = encuestaResultadoRepository.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			encuestaResultadoRepository.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
		
	}

}
