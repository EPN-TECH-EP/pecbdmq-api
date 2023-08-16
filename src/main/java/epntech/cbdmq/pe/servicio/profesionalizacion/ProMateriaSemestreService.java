package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProMateriaSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaSemestreDto;

import java.util.List;
import java.util.Optional;

public interface ProMateriaSemestreService extends ProfesionalizacionService<ProMateriaSemestre, Integer> {

      Optional<ProMateriaSemestre> findByCodMateriaAndCodPeriodoSemestre(Integer codMateria, Integer codPeriodoSemestre);
      List<ProMateriaSemestreDto> findByCodigo(Integer codigo);
}
