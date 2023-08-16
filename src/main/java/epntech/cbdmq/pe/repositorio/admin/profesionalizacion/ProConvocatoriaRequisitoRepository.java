package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoriaRequisito;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import java.util.List;
import java.util.Optional;

public interface ProConvocatoriaRequisitoRepository extends ProfesionalizacionRepository<ProConvocatoriaRequisito, Integer> {

    @Override
    Optional<ProConvocatoriaRequisito> findById(Integer integer);

    Optional<ProConvocatoriaRequisito> findByCodigoConvocatoriaAndCodigoRequisito(Integer codigoConvocatoria, Integer codigoRequisito);

    List<ProConvocatoriaRequisito> findByCodigoConvocatoria(Integer codigoConvocatoria);


}
