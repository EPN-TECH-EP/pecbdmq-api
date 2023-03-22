package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.servicio.ProvinciaService;
import epntech.cbdmq.pe.dominio.admin.Excel;
import epntech.cbdmq.pe.dominio.admin.Provincia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.helper.ProvinciaHelper;
import epntech.cbdmq.pe.repositorio.admin.ProvinciaRepository;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

	@Autowired
	private ProvinciaRepository repo;

	@Override
	public Provincia save(Provincia obj) throws DataException {
		// TODO Auto-generated method stub
		if (obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Provincia> objGuardado = repo.findByNombre(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(obj);
	}

	@Override
	public List<Provincia> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Provincia> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Provincia update(Provincia objActualizado) throws DataException {

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
	
	public void saveExcel(MultipartFile file) {
	    try {
	      List<Provincia> datos = ProvinciaHelper.excelToDatos(file.getInputStream());
	     
	      repo.saveAll(datos);
	    } catch (IOException e) {
	      throw new RuntimeException("Error al guardar los datos de excel: " + e.getMessage());
	    }
	  }

}
