package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;

public interface ResultadoPruebasService {

	void insertAll(List<ResultadoPruebas> obj);
	
	ResultadoPruebas update(ResultadoPruebas objActualizado);
	
	Optional<ResultadoPruebas> getByCodPostulanteAndPrueba(Integer CodPostulante, Integer codPrueba);
	
	void uploadFile(MultipartFile file);
	
	ResultadoPruebas save(ResultadoPruebas obj);
}
