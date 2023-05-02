package epntech.cbdmq.pe.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import epntech.cbdmq.pe.dominio.util.ApiBase;
import epntech.cbdmq.pe.dominio.util.ApiCiudadanos;
import epntech.cbdmq.pe.dominio.util.ApiEducacionMedia;
import epntech.cbdmq.pe.dominio.util.ApiEducacionSuperior;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.ApiCBDMQService;
import epntech.cbdmq.pe.util.Utilitarios;

@Service
public class APICBDMQServiceImpl implements ApiCBDMQService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Utilitarios util;

	@Value("${api.cbdmq.ciudadanos}")
	private String apiCiudadanos;
	@Value("${api.cbdmq.educacion-media}")
	private String apiEducacionMedia;
	@Value("${api.cbdmq.educacion-superior}")
	private String apiEducacionSuperior;

	public Optional<?> servicioCiudadanos(String cedula) throws Exception, DataException {
		String url = apiCiudadanos + cedula;
		Optional<?> result = Optional.empty();
		ApiBase base;
		Boolean isValid = util.validadorDeCedula(cedula);

		// System.out.println("isValid: " + isValid);
		if (isValid) {
			try {
				result = restTemplate.getForObject(url, ApiBase.class).getData();

			} catch (Exception ex) {
				// System.out.println("error: " + ex.getLocalizedMessage());

				throw new Exception(ex.getMessage());
			}
		}

		return result;

		// System.out.println("base: " + base.getStatus());

		/*
		 * if( base.getStatus().equals("error")) { throw new
		 * Exception(base.getMessage()); } else return (Optional<ApiCiudadanos>)
		 * base.getData();
		 */

	}

	@Override
	public Optional<ApiEducacionMedia> servicioEducacionMedia(String cedula) throws Exception {

		Optional<ApiEducacionMedia> result = Optional.empty();
		Boolean isValid = util.validadorDeCedula(cedula);

		// System.out.println("isValid: " + isValid);
		if (isValid) {
			String url = apiEducacionMedia + cedula;
			ApiBase base = restTemplate.getForObject(url, ApiBase.class);

			if (base.getStatus().equals("error"))
				throw new Exception(base.getMessage());
			else
				result = (Optional<ApiEducacionMedia>) base.getData();
		}

		return result;

	}

	@Override
	public Optional<ApiEducacionSuperior> servicioEducacionSuperior(String cedula) throws Exception {

		Optional<ApiEducacionSuperior> result = Optional.empty();
		
		Boolean isValid = util.validadorDeCedula(cedula);
		// System.out.println("isValid: " + isValid);
		if (isValid) {
			String url = apiEducacionMedia + cedula;
			ApiBase base = restTemplate.getForObject(url, ApiBase.class);

			if (base.getStatus().equals("error"))
				throw new Exception(base.getMessage());
			else
				result = (Optional<ApiEducacionSuperior>) base.getData();
		}
		
		return result;
	}
}
