package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Semestre;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.SemestreRepository;
import epntech.cbdmq.pe.servicio.SemestreService;

@Service
public class SemestreServiceImpl implements SemestreService {

	@Autowired
	private SemestreRepository repo;

	@Override
	public Semestre save(Semestre obj) throws DataException {
		if(obj.getSemestre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Semestre> objGuardado = repo.findBySemestreIgnoreCase(obj.getSemestre());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			Semestre stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
			throw new DataException(REGISTRO_YA_EXISTE);
			}

		}
		return repo.save(obj);	
	}

	@Override
	public List<Semestre> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Semestre> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Semestre update(Semestre objActualizado) throws DataException  {
		Optional<Semestre> objGuardado = repo.findBySemestreIgnoreCase(objActualizado.getSemestre());
		if (objGuardado.isPresent()&& !objGuardado.get().getCodSemestre().equals(objActualizado.getCodSemestre())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(objActualizado);
	}

	@Override
	public void deleteById(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}
}
