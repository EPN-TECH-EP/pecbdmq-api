package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.EvaluacionDocente;
import epntech.cbdmq.pe.dominio.admin.TipoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EvaluacionDocenteRepository;
import epntech.cbdmq.pe.servicio.EvaluacionDocenteService;

@Service
public class EvaluacionDocenteServiceImpl implements EvaluacionDocenteService {

	@Autowired
	EvaluacionDocenteRepository repo;
	
	@Override
	public EvaluacionDocente save(EvaluacionDocente obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<EvaluacionDocente> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			EvaluacionDocente stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
			throw new DataException(REGISTRO_YA_EXISTE);
			}

		}
		obj.setNombre(obj.getNombre().toUpperCase());
        return repo.save(obj);
	}

	@Override
	public List<EvaluacionDocente> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<EvaluacionDocente> getById(Integer codigo) {
		// TODO Auto-generated method stub
		 return repo.findById(codigo);
	}

	@Override
	public EvaluacionDocente update(EvaluacionDocente objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

	
	
	
	
}
