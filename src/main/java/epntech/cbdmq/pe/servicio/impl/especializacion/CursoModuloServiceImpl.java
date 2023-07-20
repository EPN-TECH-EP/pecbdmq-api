package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.EspecializacionConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModulo;
import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoModuloRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoModuloService;

@Service
public class CursoModuloServiceImpl implements CursoModuloService {
	
	@Autowired
	private CursoModuloRepository cursoModuloRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@Override
	public CursoModulo save(CursoModulo cursoModulo) throws DataException {
		Optional<Curso> curso = cursoRepository.getCursoAprobado(cursoModulo.getCodCursoEspecializacion());
		if(curso.isEmpty())
			throw new DataException(CURSO_NO_APROBADO);
		if(!curso.get().getTieneModulos())
			throw new DataException(CURSO_NO_MODULO);
		
		Optional<CursoModulo> cursoModuloOptional = cursoModuloRepository.findByCodCursoEspecializacionAndCodEspModulo(cursoModulo.getCodCursoEspecializacion(), cursoModulo.getCodEspModulo());
		if(cursoModuloOptional.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);
	
		return cursoModuloRepository.save(cursoModulo);
	}

	@Override
	public CursoModulo update(CursoModulo cursoModuloActualizado) throws DataException {
		Optional<Curso> curso = cursoRepository.getCursoAprobado(cursoModuloActualizado.getCodCursoEspecializacion());
		if(curso.isEmpty())
			throw new DataException(CURSO_NO_APROBADO);
		if(!curso.get().getTieneModulos())
			throw new DataException(CURSO_NO_MODULO);
		
		Optional<CursoModulo> cursoModuloOptional = cursoModuloRepository.findByCodCursoEspecializacionAndCodEspModulo(cursoModuloActualizado.getCodCursoEspecializacion(), cursoModuloActualizado.getCodEspModulo());
		if(cursoModuloOptional.isPresent() && !cursoModuloActualizado.getCodCursoModulo().equals(cursoModuloOptional.get().getCodCursoModulo()))
			throw new DataException(REGISTRO_YA_EXISTE);
		
		return cursoModuloRepository.save(cursoModuloActualizado);
	}

	@Override
	public List<CursoModuloDatosEspecializacion> listAll() {
		// TODO Auto-generated method stub
		return cursoModuloRepository.getAllCursoModulo();
	}

	@Override
	public Optional<CursoModulo> getById(Long codCursoModulo) throws DataException {
		Optional<CursoModulo> cursoModuloOptional = cursoModuloRepository.findById(codCursoModulo);
		if(cursoModuloOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
			
		return cursoModuloRepository.findById(codCursoModulo);
	}

	@Override
	public void delete(Long codCursoModulo)  throws DataException {
		Optional<CursoModulo> cursoModuloOptional = cursoModuloRepository.findById(codCursoModulo);
		if(cursoModuloOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		cursoModuloRepository.deleteById(codCursoModulo);
		
	}

	@Override
	public List<CursoModulo> getByCurso(Long codCurso) throws DataException {
		Optional<CursoModulo> cursoModuloOptional = cursoModuloRepository.findById(codCurso);
		if(cursoModuloOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		return cursoModuloRepository.findByCodCursoEspecializacion(codCurso);
	}

}
