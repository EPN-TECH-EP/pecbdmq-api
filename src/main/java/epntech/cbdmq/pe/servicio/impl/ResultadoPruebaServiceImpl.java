package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.ResultadoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebaRepository;
import epntech.cbdmq.pe.servicio.ResultadoPruebaService;

@Service
public class ResultadoPruebaServiceImpl implements ResultadoPruebaService{

	@Autowired
	ResultadoPruebaRepository repo;
	
	
	@Override
	public ResultadoPrueba save(ResultadoPrueba obj) throws DataException {
		if(obj.getCumplePrueba().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<ResultadoPrueba> objGuardado = repo.findByCumplePrueba(obj.getCumplePrueba());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			ResultadoPrueba stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
			throw new DataException(REGISTRO_YA_EXISTE);
			}

		}
		/*if(obj.get()>=0.8) {
			throw new DataException(CURSO_APROBADO);
		}else {
			throw new DataException(CURSO_REPROBADO);
		}*/
		return repo.save(obj);
	}

	@Override
	public List<ResultadoPrueba> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<ResultadoPrueba> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public ResultadoPrueba update(ResultadoPrueba objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

}
