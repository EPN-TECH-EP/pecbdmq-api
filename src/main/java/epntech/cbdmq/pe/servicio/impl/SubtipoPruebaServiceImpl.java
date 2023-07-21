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
import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.dominio.util.SubTipoPruebaDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.SubTipoPruebaRepository;
import epntech.cbdmq.pe.servicio.SubtipoPruebaService;

@Service
public class SubtipoPruebaServiceImpl implements SubtipoPruebaService {

	@Autowired
	private SubTipoPruebaRepository repo;

	@Override
	public SubTipoPrueba save(SubTipoPrueba obj) throws DataException {

		if (obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<SubTipoPrueba> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			SubTipoPrueba stp = objGuardado.get();
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
	public List<SubTipoPrueba> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll(SubTipoPruebaRepository.defaultSort);
	}

	@Override
	public Optional<SubTipoPrueba> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public SubTipoPrueba update(SubTipoPrueba objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return this.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
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

	@Override
	public List<SubTipoPruebaDatos> listarTodosConDatosTipoPrueba() {
		return this.repo.listarTodosConDatosTipoPrueba();
	}

	@Override
	public List<SubTipoPrueba> getAllByCodTipoPrueba(Long codTipoPrueba) {
		return this.repo.listarAllByCodTipoPruebaOrderByNombre(codTipoPrueba);
	}

}
