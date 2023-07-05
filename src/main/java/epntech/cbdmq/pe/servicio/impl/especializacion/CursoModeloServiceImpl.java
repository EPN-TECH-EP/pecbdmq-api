package epntech.cbdmq.pe.servicio.impl.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModelo;
import epntech.cbdmq.pe.servicio.especializacion.CursoModeloService;

@Service
public class CursoModeloServiceImpl implements CursoModeloService {

	@Override
	public CursoModelo save(CursoModelo cursoModelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CursoModelo update(CursoModelo cursoModeloActualizado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CursoModelo> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CursoModelo> getById(Long codCursoModelo) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Long codCursoModelo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CursoModelo> getByCurso(Long codCursoModelo) {
		// TODO Auto-generated method stub
		return null;
	}

}
