package epntech.cbdmq.pe.servicio.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EspecializacionHistoricoService {
    List<EspecializacionEstudiante> getEspecializacionHistoricos(String codEstudiante, Pageable pageable);

    List<EspecializacionInstructor> getHistoricoInstructor(Integer codInstructor, Pageable pageable);
}
