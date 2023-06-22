package epntech.cbdmq.pe.servicio.impl.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModulo;
import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoModuloRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoModuloService;

@Service
public class CursoModuloServiceImpl implements CursoModuloService {
	
	@Autowired
	private CursoModuloRepository cursoModuloRepository;

	@Override
	public CursoModulo save(CursoModulo cursoModulo) {
		
		return cursoModuloRepository.save(cursoModulo);
	}

	@Override
	public CursoModulo update(CursoModulo cursoModuloActualizado) {
		// TODO Auto-generated method stub
		return cursoModuloRepository.save(cursoModuloActualizado);
	}

	@Override
	public List<CursoModuloDatosEspecializacion> listAll() {
		// TODO Auto-generated method stub
		return cursoModuloRepository.getAllCursoModulo();
	}

	@Override
	public Optional<CursoModulo> getById(Long codCursoModulo) {
		// TODO Auto-generated method stub
		return cursoModuloRepository.findById(codCursoModulo);
	}

	@Override
	public void delete(Long codCursoModulo) {
		cursoModuloRepository.deleteById(codCursoModulo);
		
	}

	@Override
	public List<CursoModulo> getByCurso(Long codCurso) {
		// TODO Auto-generated method stub
		return cursoModuloRepository.findByCodCursoEspecializacion(codCurso);
	}

}
