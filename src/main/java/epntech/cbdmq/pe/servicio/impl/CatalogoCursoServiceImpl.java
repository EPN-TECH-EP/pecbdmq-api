package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursoRepository;
import epntech.cbdmq.pe.servicio.CatalogoCursoService;

@Service
public class CatalogoCursoServiceImpl implements CatalogoCursoService {
	@Autowired
	CatalogoCursoRepository repo;
	
	
	@Override
	public CatalogoCurso save(CatalogoCurso obj) throws DataException {
		// TODO Auto-generated method stub
				if (obj.getNombre().trim().isEmpty())
					throw new DataException(REGISTRO_VACIO);
				Optional<CatalogoCurso> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
				if (objGuardado.isPresent()) {
					throw new DataException(REGISTRO_YA_EXISTE);
				}
				obj.setNombre(obj.getNombre().toUpperCase());
				return repo.save(obj);
	}

	@Override
	public List<CatalogoCurso> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<CatalogoCurso> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public CatalogoCurso update(CatalogoCurso objActualizado) throws DataException {
		if(objActualizado.getNombre() !=null) {
			Optional<CatalogoCurso> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
			if (objGuardado.isPresent()&& !objGuardado.get().getCodCatalogoCursos().equals(objActualizado.getCodCatalogoCursos())) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}
			return repo.save(objActualizado);
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

}
