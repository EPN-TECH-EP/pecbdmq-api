package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.dominio.admin.Grado;
import epntech.cbdmq.pe.dominio.admin.Rango;

import java.util.List;

public interface GradoService {
    List<Grado> getAll();
    List<Rango> getRangoByGrado(Integer codGrado);
    Grado findByNombre(String nombre);
}
