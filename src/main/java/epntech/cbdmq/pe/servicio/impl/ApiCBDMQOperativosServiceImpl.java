package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.dominio.util.ApiBaseFuncionario;
import epntech.cbdmq.pe.dominio.util.ApiBaseOperativos;
import epntech.cbdmq.pe.dominio.util.FuncionarioApiDto;
import epntech.cbdmq.pe.dominio.util.OperativoApiDto;
import epntech.cbdmq.pe.servicio.ApiCBDMQOperativosService;
import epntech.cbdmq.pe.servicio.DatoPersonalService;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.FuncionarioService;
import epntech.cbdmq.pe.util.Utilitarios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_PROSPECTO;

@Service
public class ApiCBDMQOperativosServiceImpl implements ApiCBDMQOperativosService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FuncionarioService funcionarioService;

    @Value("${api.cbdmq.operativos}")
    private String apiFuncionarios;


    @Override
    public List<Funcionario> servicioOperativosAndNoOperativos() throws Exception {
        ApiBaseOperativos base;
        String url = apiFuncionarios;

        try {
            base = restTemplate.getForObject(url, ApiBaseOperativos.class);


            List<Funcionario> funcionario = base.getData();
            return funcionario;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Capturamos la excepción y devolvemos Optional.empty() cuando hay un error HTTP
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public Boolean guardarInBD() throws Exception {

        List<Funcionario> funcionarios = this.servicioOperativosAndNoOperativos();
        //TODO poner que no se carge todos los 2k registros sino que haga desde el ultimo
        //TODO ponerlo con dato personal
        funcionarioService.saveAll(funcionarios);
        return true;
    }

}
