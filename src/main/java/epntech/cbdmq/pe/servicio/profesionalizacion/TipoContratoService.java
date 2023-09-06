package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.TipoContrato;

import java.util.Optional;

public interface TipoContratoService extends ProfesionalizacionService<TipoContrato, Integer> {
       Optional<TipoContrato> findByNombreTipo (String nombre);
}
