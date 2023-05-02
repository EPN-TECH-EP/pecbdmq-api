package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosLista;

public interface ValidacionRequisitosService {

	List<ValidacionRequisitosLista> getRequisitos(Integer codPostulante);
}
