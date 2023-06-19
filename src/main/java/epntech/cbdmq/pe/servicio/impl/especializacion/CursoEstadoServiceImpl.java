package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoEstadoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoEstadoService;

@Service
public class CursoEstadoServiceImpl implements CursoEstadoService {
	
	@Autowired
	private CursoEstadoRepository cursoEstadoRepository; 

	@Override
	public CursoEstado save(CursoEstado cursoEstado) throws DataException {
		Optional<CursoEstado> cursoEstadoOptional;
		
		cursoEstadoOptional = cursoEstadoRepository.getByCursoYCatalogoEstado(cursoEstado.getCodCursoEspecializacion(), cursoEstado.getCodCatalogoEstados());
		if(cursoEstadoOptional.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);
		cursoEstadoOptional = cursoEstadoRepository.getByCursoYOrden(cursoEstado.getCodCursoEspecializacion(), cursoEstado.getOrden());
		if(cursoEstadoOptional.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);
		
		return cursoEstadoRepository.save(cursoEstado);
	}

	@Override
	public CursoEstado update(CursoEstado cursoEstadoActualizado)  throws DataException{
		Optional<CursoEstado> cursoEstadoOptional;
		
		cursoEstadoOptional = cursoEstadoRepository.getByCursoYCatalogoEstado(cursoEstadoActualizado.getCodCursoEspecializacion(), cursoEstadoActualizado.getCodCatalogoEstados());
		
		if(cursoEstadoOptional.isPresent() && !cursoEstadoOptional.get().getCodCursoEstado().equals(cursoEstadoActualizado.getCodCursoEstado()))
			throw new DataException(REGISTRO_YA_EXISTE);
		cursoEstadoOptional = cursoEstadoRepository.getByCursoYOrden(cursoEstadoActualizado.getCodCursoEspecializacion(), cursoEstadoActualizado.getOrden());
		if(cursoEstadoOptional.isPresent()  && !cursoEstadoOptional.get().getCodCursoEstado().equals(cursoEstadoActualizado.getCodCursoEstado()))
			throw new DataException(REGISTRO_YA_EXISTE);
		
		return cursoEstadoRepository.save(cursoEstadoActualizado);
	}

	@Override
	public List<CursoEstado> listarTodo() {
		// TODO Auto-generated method stub
		return cursoEstadoRepository.findAll();
	}

	@Override
	public Optional<CursoEstado> getById(Long codCursoEstado) {
		// TODO Auto-generated method stub
		return cursoEstadoRepository.findById(codCursoEstado);
	}

	@Override
	public void delete(Long codCursoEstado) {
		cursoEstadoRepository.deleteById(codCursoEstado);
		
	}

}
