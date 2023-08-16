package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoEstudiante;

public interface ProPeriodoEstudianteService extends ProfesionalizacionService<ProPeriodoEstudiante, Integer>{
    public UsuarioDatoPersonal findByCedula(String cedula);
}
