package epntech.cbdmq.pe.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
//@ConfigurationProperties
@Data
public class ConfigProperties {

/*	// ARCHIVOS
	@Value("${spring.servlet.multipart.max-file-size}")
	public String TAMAÑO_MÁXIMO;

	// EMAIL
	@Value("${pecb.email.username}")
	public String USERNAME;

	@Value("${pecb.email.password}")
	public String PASSWORD;
*/
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
