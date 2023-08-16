package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoriaRequisito;

import java.util.Optional;

public interface ProConvocatoriaRequisitoService extends ProfesionalizacionService<ProConvocatoriaRequisito, Integer> {

    Optional<ProConvocatoriaRequisito> findByConvocatoriaAndRequisito(Integer codigoConvocatoria, Integer codigoRequisito);


}
