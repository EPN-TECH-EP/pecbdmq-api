package epntech.cbdmq.pe.servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;
import epntech.cbdmq.pe.helper.ExelhelperInscripcionvalida;
import epntech.cbdmq.pe.repositorio.admin.InscripcionesValidasRepository;

@Service
public class ExelInscripcionValidaService {

	@Autowired
	private InscripcionesValidasRepository repository;
	
	public void save(MultipartFile file) {
	    try {
	      List<InscripcionesValidasUtil> datos = ExelhelperInscripcionvalida.excelToDatos(file.getInputStream());
	     
	      repository.saveAll(datos);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	  }
	
	 public ByteArrayInputStream load() {
		    List<InscripcionesValidasUtil> datos = repository.getinscripcioneslistar();

		    ByteArrayInputStream in = ExelhelperInscripcionvalida.datosToExcel(datos);
		    return in;
		  }
	
	public List<InscripcionesValidasUtil> getAllDatos() {
	    return repository.getinscripcioneslistar();
	  }
	
}
