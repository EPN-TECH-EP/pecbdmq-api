package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.util.ApiBaseFuncionario;
import epntech.cbdmq.pe.dominio.util.ApiBaseOperativos;
import epntech.cbdmq.pe.dominio.util.FuncionarioApiDto;
import epntech.cbdmq.pe.dominio.util.OperativoApiDto;
import epntech.cbdmq.pe.servicio.ApiCBDMQOperativosService;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

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
    private EmailService emailService;

    @Value("${api.cbdmq.operativos}")
    private String apiFuncionarios;


    @Override
    public List<OperativoApiDto> servicioOperativosAndNoOperativos() throws Exception {
        ApiBaseOperativos base;
        String url = apiFuncionarios;

        try {
            base = restTemplate.getForObject(url, ApiBaseOperativos.class);

            if ("error".equals(base.getStatus()) && "cedula incorrecta".equals(base.getMessage())) {
                // Si la cédula no es correcta, devolvemos Optional.empty()
                return null;
            } else {
                List<OperativoApiDto> funcionario = base.getData();
                return funcionario;
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Capturamos la excepción y devolvemos Optional.empty() cuando hay un error HTTP
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public List<OperativoApiDto> servicioOperativos() throws Exception {
        return this.servicioOperativosAndNoOperativos().stream().filter(dto -> Boolean.TRUE.equals(dto.getOperativo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OperativoApiDto> servicioOperativosOrderByAntiguedad() throws Exception {
        return this.servicioOperativosAndNoOperativos().stream()
                .filter(dto -> Boolean.TRUE.equals(dto.getOperativo()))
                .sorted(Comparator.comparing(OperativoApiDto::getFechaIngreso))
                .collect(Collectors.toList());
    }

    @Override
    public void notificarMejoresProspectos(int numeroLimite) throws Exception {
        List<OperativoApiDto> result = this.servicioOperativosAndNoOperativos().stream()
                .filter(dto -> Boolean.TRUE.equals(dto.getOperativo()))
                .sorted(Comparator.comparing(OperativoApiDto::getFechaIngreso))
                .limit(numeroLimite)
                .collect(Collectors.toList());
        for(OperativoApiDto dto : result){
            emailService.sendMensajeTextGenerico(dto.getEmail(), EMAIL_SUBJECT_PROSPECTO, "Usted ha sido seleccionado como uno de los mejores prospectos de la institución para ganar un curso de ascenso. Comuníquese con la dirección de comunicación para mayor información.");
        }


    }

    @Override
    public void notificarOperativoComoMejorProspecto(String correo) {
        emailService.sendMensajeTextGenerico(correo, EMAIL_SUBJECT_PROSPECTO, "Usted ha sido seleccionado como uno de los mejores prospectos de la institución para ganar un curso de ascenso. Comuníquese con la dirección de comunicación para mayor información.");


    }

}
