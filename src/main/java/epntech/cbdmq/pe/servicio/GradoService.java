package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.dominio.admin.Grado;
import epntech.cbdmq.pe.dominio.admin.Rango;
import epntech.cbdmq.pe.dominio.util.RangoDtoRead;

import java.util.List;

public interface GradoService {
    List<Grado> getAll();
    List<RangoDtoRead> getRangoByGrado(Integer codGrado);
}
