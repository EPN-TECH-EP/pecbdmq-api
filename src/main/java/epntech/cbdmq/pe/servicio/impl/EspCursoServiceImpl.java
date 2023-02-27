package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EspCursoRepository;
import epntech.cbdmq.pe.servicio.EspCursoService;

@Service
public class EspCursoServiceImpl implements EspCursoService {

	@Autowired
	private EspCursoRepository repo;

	@Override
	public EspCurso save(EspCurso obj) throws DataException {
		if (obj.getNombrecursoespecializacion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		// Optional<EspCurso> objGuardado =
		// repo.findBynombrecursoespecializacion(obj.getNombrecursoespecializacion());
		Optional<EspCurso> objGuardado = repo.findBynombrecursoespecializacion(obj.getNombrecursoespecializacion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(obj);
	}

	@Override
	public List<EspCurso> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<EspCurso> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public EspCurso update(EspCurso objActualizado) throws DataException {
		if (objActualizado.getNombrecursoespecializacion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<EspCurso> objGuardado = repo
				.findBynombrecursoespecializacion(objActualizado.getNombrecursoespecializacion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {

		repo.deleteById(codigo);
	}

}
