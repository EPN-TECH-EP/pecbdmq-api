package epntech.cbdmq.pe.servicio.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    @Override
    public List<CiudadanoApiDto> servicioCiudadanos(String cedula) throws Exception {

        String url = apiCiudadanos + cedula;
        ApiBaseCiudadano base;
        Boolean isValid = util.validadorDeCedula(cedula);

        if (isValid) {
            try {
                base = restTemplate.getForObject(url, ApiBaseCiudadano.class);
                List<CiudadanoApiDto> ciudadanos = base.getData();

                return ciudadanos;

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }

        return Collections.emptyList(); // Retorna una lista vacía si no se encontraron ciudadanos
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
			String url = apiEducacionSuperior + cedula;
			ApiBase base = restTemplate.getForObject(url, ApiBase.class);

			if (base.getStatus().equals("error"))
				throw new Exception(base.getMessage());
			else
				result = (Optional<ApiEducacionSuperior>) base.getData();
		}
		
		return result;
	}

}
