package epntech.cbdmq.pe.servicio.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FormacionHistoricoService {
    List<FormacionEstudiante> getHistoricos(String codEstudiante, Pageable pageable);

    List<FormacionInstructor> getFormHistoricos(Integer codEstudiante, Pageable pageable);
}
