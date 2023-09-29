package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.FuncionarioRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_PROSPECTO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    @Autowired
    private FuncionarioRepository repo;
    @Autowired
    private EmailService emailService;
    @Override
    public List<Funcionario> saveAll(List<Funcionario> funcionarios) {
        return repo.saveAll(funcionarios);
    }

    @Override
    public Funcionario save(Funcionario funcionario) throws DataException {
        return repo.save(funcionario);
    }

    @Override
    public List<Funcionario> listAll() {
        return repo.findAll();
    }

    @Override
    public void notificarMejoresProspectos(int numeroLimite) throws Exception {
        List<Funcionario> result = this.listAll().stream()
                .filter(dto -> Boolean.TRUE.equals(dto.getOperativo()))
                .sorted(Comparator.comparing(Funcionario::getFechaIngreso))
                .limit(numeroLimite)
                .collect(Collectors.toList());
        for (Funcionario dto : result) {
            emailService.sendMensajeTextGenerico(dto.getEmail(), EMAIL_SUBJECT_PROSPECTO, "Usted ha sido seleccionado como uno de los mejores prospectos de la institución para ganar un curso de ascenso. Comuníquese con la dirección de comunicación para mayor información.");
        }
    }
    @Override
    public void notificarOperativoComoMejorProspecto(String correo) {
        emailService.sendMensajeTextGenerico(correo, EMAIL_SUBJECT_PROSPECTO, "Usted ha sido seleccionado como uno de los mejores prospectos de la institución para ganar un curso de ascenso. Comuníquese con la dirección de comunicación para mayor información.");


    }

    @Override
    public List<Funcionario> servicioOperativos() throws Exception {
          return this.listAll().stream().filter(dto -> Boolean.TRUE.equals(dto.getOperativo()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Funcionario> servicioOperativosOrderByAntiguedad() throws Exception {
        return this.listAll().stream()
                .filter(dto -> Boolean.TRUE.equals(dto.getOperativo()))
                .sorted(Comparator.comparing(Funcionario::getFechaIngreso))
                .collect(Collectors.toList());
    }
}
