package epntech.cbdmq.pe.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import epntech.cbdmq.pe.dominio.admin.ApiBase;
import epntech.cbdmq.pe.dominio.admin.ApiCiudadanos;
import epntech.cbdmq.pe.dominio.admin.ApiEducacionMedia;
import epntech.cbdmq.pe.dominio.admin.ApiEducacionSuperior;

import epntech.cbdmq.pe.servicio.ApiCBDMQService;

@Service
public class APICBDMQServiceImpl implements ApiCBDMQService {

	@Autowired
    private RestTemplate restTemplate;
	
	@Value("${api.cbdmq.ciudadanos}")
	private String apiCiudadanos;
	@Value("${api.cbdmq.educacion-media}")
	private String apiEducacionMedia;
	@Value("${api.cbdmq.educacion-superior}")
	private String apiEducacionSuperior;
	
	public Optional<?> servicioCiudadanos(String cedula) throws Exception {
        String url = apiCiudadanos + cedula;
        Optional<ApiCiudadanos> result;
        ApiBase base;

        try {
        	return restTemplate.getForObject(url, ApiBase.class).getData();

        }catch(Exception ex) {
        	//System.out.println("error: " + ex.getLocalizedMessage());

        	throw new Exception(ex.getMessage());
        }
        
        
        //System.out.println("base: " + base.getStatus());
        
        /*if( base.getStatus().equals("error")) {
        	throw new Exception(base.getMessage());
        }
        else return (Optional<ApiCiudadanos>) base.getData();*/
        
    }

	@Override
	public Optional<ApiEducacionMedia> servicioEducacionMedia(String cedula) throws Exception {
		String url = apiEducacionMedia + cedula;
		ApiBase base = restTemplate.getForObject(url, ApiBase.class);
		
		if( base.getStatus().equals("error"))
        	throw new Exception(base.getMessage());
		else
			return (Optional<ApiEducacionMedia>) base.getData();
		
	}

	@Override
	public Optional<ApiEducacionSuperior> servicioEducacionSuperior(String cedula) throws Exception {
		String url = apiEducacionMedia + cedula;
		ApiBase base = restTemplate.getForObject(url, ApiBase.class);
		
		if( base.getStatus().equals("error"))
        	throw new Exception(base.getMessage());
		else
			return (Optional<ApiEducacionSuperior>) base.getData();
	}
}
