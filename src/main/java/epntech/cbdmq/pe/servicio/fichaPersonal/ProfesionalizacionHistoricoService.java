package epntech.cbdmq.pe.servicio.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfesionalizacionHistoricoService {
    List<ProfesionalizacionEstudiante> getProfesionalizacionHistoricos(String codEstudiante, Pageable pageable);

    List<ProfesionalizacionInstructor> getProfesionalizacionHistoricos(Integer codInstructor, Pageable pageable);

}
