package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.admin.ConvocatorialistarRepository;



import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;



import epntech.cbdmq.pe.servicio.ConvocatoriaService;

@Service
public class ConvocatoriaServicieImpl implements ConvocatoriaService{

	@Autowired
	private ConvocatoriaRepository repo;
	
	@Autowired

	private ConvocatorialistarRepository repo1;

	
	@Override
	public Convocatoria saveData(Convocatoria obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<?> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
	}

	@Override
	public List<Convocatoria> getAllData() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Convocatoria> getByIdData(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Convocatoria updateData(Convocatoria objActualizado)throws DataException  {
		if(objActualizado.getNombre() !=null) {
			Optional<Convocatoria> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
			if (objGuardado.isPresent()&& !objGuardado.get().getCodConvocatoria().equals(objActualizado.getCodConvocatoria())) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}
			return repo.save(objActualizado);
		}

	@Override
	public void deleteData(int id) throws DataException {
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

	public List<Convocatorialistar> getConvocatorialistar() {
		// TODO Auto-generated method stub
		return repo1.getConvocatorialistar();

	}

	@Override
	public Set<Convocatoria> getConvocatoriaActiva() {
		// TODO Auto-generated method stub
		return repo.getConvocatoriaActiva();
	}

}
