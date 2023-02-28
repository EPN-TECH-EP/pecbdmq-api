package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.servicio.DatoPersonalService;

@Service
public class DatoPersonalServiceImpl implements DatoPersonalService {

	@Autowired
	private DatoPersonalRepository repo;
	
	@Override
	public DatoPersonal saveDatosPersonales(DatoPersonal obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<DatoPersonal> objGuardado = repo.findOneByCedula(obj.getCedula());
		if (objGuardado.isPresent()) {
			throw new DataException(CEDULA_YA_EXISTE);
		}
		
        return repo.save(obj);
	}

	@Override
	public List<DatoPersonal> getAllDatosPersonales() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Page<DatoPersonal> getAllDatosPersonales(Pageable pageable) throws Exception{
		try {
			return repo.findAll(pageable);
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	@Override
	public Optional<DatoPersonal> getDatosPersonalesById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}
	
	public Optional<DatoPersonal> getByCedula(String cedula) {
		// TODO Auto-generated method stub
		return repo.findOneByCedula(cedula);
	}

	@Override
	public DatoPersonal updateDatosPersonales(DatoPersonal objActualizado) throws DataException {
		
		return repo.save(objActualizado);
	}
	
	@Override
    public Page<DatoPersonal> search(String filtro, Pageable pageable) throws Exception {
        try {
            //Page<DatosPersonales> datos = repo.findByNombreContainingOrApellidoContaining(filtro, filtro, pageable);
            //Page<Persona> datos = personaRepository.search(filtro, pageable);
            Page<DatoPersonal> datos = repo.searchNativo(filtro, pageable);
            return datos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
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
