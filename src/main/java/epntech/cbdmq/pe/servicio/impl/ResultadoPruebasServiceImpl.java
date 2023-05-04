package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.helper.ResultadoPruebasHelper;
import epntech.cbdmq.pe.repositorio.admin.PruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasRepository;
import epntech.cbdmq.pe.servicio.ResultadoPruebasService;

@Service
public class ResultadoPruebasServiceImpl implements ResultadoPruebasService {

	@Autowired
	private ResultadoPruebasRepository repo;
	@Autowired
	private PruebaRepository pruebaRepository;

	@Override
	public void insertAll(List<ResultadoPruebas> obj) {
		repo.saveAll(obj);

	}

	@Override
	public ResultadoPruebas update(ResultadoPruebas objActualizado) {
		ResultadoPruebas resultadoPruebas = new ResultadoPruebas();
		Optional<Prueba> prueba = pruebaRepository.findById(objActualizado.getCodPrueba());
		if (prueba.isPresent()) {
			resultadoPruebas = repo.save(objActualizado);

			Prueba p = new Prueba();
			p = prueba.get();
			p.setEstado("REGISTRO");

			pruebaRepository.save(p);
		}

		return resultadoPruebas;
	}

	@Override
	public Optional<ResultadoPruebas> getByCodPostulanteAndPrueba(Integer CodPostulante, Integer codPrueba) {
		// TODO Auto-generated method stub
		return repo.findByCodPostulanteAndCodPrueba(CodPostulante, codPrueba);
	}

	@Override
	public void uploadFile(MultipartFile file) {
		try {
			
			List<ResultadoPruebas> datos = ResultadoPruebasHelper.excelToDatos(file.getInputStream());
			
			repo.saveAll(datos);
		} catch (IOException e) {
			throw new RuntimeException("Falla al cargar el archivo " + e.getMessage());
		}

	}

	@Override
	public ResultadoPruebas save(ResultadoPruebas obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

}
