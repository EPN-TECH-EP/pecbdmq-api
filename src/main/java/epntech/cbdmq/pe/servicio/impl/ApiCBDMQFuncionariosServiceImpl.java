package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.util.ApiBaseFuncionario;
import epntech.cbdmq.pe.dominio.util.FuncionarioApiDto;
import epntech.cbdmq.pe.servicio.ApiCBDMQFuncionariosService;
import epntech.cbdmq.pe.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@Service
public class ApiCBDMQFuncionariosServiceImpl implements ApiCBDMQFuncionariosService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Utilitarios util;

    @Value("${api.cbdmq.funcionarios}")
    private String apiFuncionarios;
    @Override
    public Optional<FuncionarioApiDto> servicioFuncionarios(String cedula) throws Exception {
        String url = apiFuncionarios + cedula;
        ApiBaseFuncionario base;
        Boolean isValid = util.validadorDeCedula(cedula);

        if (isValid) {
            try {
                base = restTemplate.getForObject(url, ApiBaseFuncionario.class);
                FuncionarioApiDto funcionario = base.getData();

                return Optional.ofNullable(funcionario);
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }

        return Optional.empty();
    }


}
