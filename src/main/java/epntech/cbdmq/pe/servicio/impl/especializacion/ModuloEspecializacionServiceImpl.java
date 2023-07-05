package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.ModuloEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ModuloEspecializacionRepository;
import epntech.cbdmq.pe.servicio.especializacion.ModuloEspecializacionService;

@Service
public class ModuloEspecializacionServiceImpl implements ModuloEspecializacionService {
	
	@Autowired
	private ModuloEspecializacionRepository moduloEspecializacionRepository;

	@Override
	public ModuloEspecializacion save(ModuloEspecializacion moduloEspecializacion) throws DataException {
		if (moduloEspecializacion.getNombreEspModulo().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<ModuloEspecializacion> objGuardado = moduloEspecializacionRepository.findByNombreEspModuloIgnoreCase(moduloEspecializacion.getNombreEspModulo());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		moduloEspecializacion.setEstado("ACTIVO");
		return moduloEspecializacionRepository.save(moduloEspecializacion);
	}

	@Override
	public ModuloEspecializacion update(ModuloEspecializacion moduloEspecializacionActualizado) throws DataException {
		Optional<ModuloEspecializacion> objGuardado = moduloEspecializacionRepository.findByNombreEspModuloIgnoreCase(moduloEspecializacionActualizado.getNombreEspModulo());
		if (objGuardado.isPresent() && !objGuardado.get().getCodEspModulo().equals(moduloEspecializacionActualizado.getCodEspModulo())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return moduloEspecializacionRepository.save(moduloEspecializacionActualizado);
	}

	@Override
	public List<ModuloEspecializacion> getAll() {
		// TODO Auto-generated method stub
		return moduloEspecializacionRepository.findAll();
	}

	@Override
	public Optional<ModuloEspecializacion> getById(Long codModulo) {
		// TODO Auto-generated method stub
		return moduloEspecializacionRepository.findById(codModulo);
	}

	@Override
	public void delete(Long codModulo) {
		moduloEspecializacionRepository.deleteById(codModulo);
		
	}

}
