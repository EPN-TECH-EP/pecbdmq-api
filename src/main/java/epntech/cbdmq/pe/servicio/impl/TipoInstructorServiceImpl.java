package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.TipoInstructor;
import epntech.cbdmq.pe.repositorio.admin.TipoInstructorRepository;
import epntech.cbdmq.pe.servicio.TipoInstructorService;

@Service
public class TipoInstructorServiceImpl implements TipoInstructorService{

	  @Autowired
	  TipoInstructorRepository repo;
	
	@Override
	public TipoInstructor save(TipoInstructor obj) {
		obj.setNombre(obj.getNombre().toUpperCase());

		Optional<TipoInstructor> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			// valida si existe eliminado
			TipoInstructor stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
				throw new BusinessException(REGISTRO_YA_EXISTE);
			}
		}

		return repo.save(obj);
	}

	@Override
	public List<TipoInstructor> getAll() {
		return repo.findAll();
	}

	@Override
	public TipoInstructor getById(Integer codigo) {
		return repo.findById(codigo)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public TipoInstructor update(TipoInstructor objActualizado) {
		TipoInstructor tipoInstructor = repo.findById(objActualizado.getCodigo())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		tipoInstructor.setNombre(objActualizado.getNombre());
		tipoInstructor.setEstado(objActualizado.getEstado());

		Optional<TipoInstructor> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		if (objGuardado.isPresent()) {
			throw new BusinessException(REGISTRO_YA_EXISTE);
		}
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		 repo.deleteById(codigo);
	}

}
