package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;


import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProMateriaSemestre;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import java.util.Optional;

public interface ProMateriaSemestreRepository extends ProfesionalizacionRepository<ProMateriaSemestre, Integer> {
    Optional<ProMateriaSemestre> findById (Integer Codigo);

    Optional<ProMateriaSemestre> findByCodMateriaAndCodPeriodoSemestre(int codigoMateria, int codigoSemestre);

}
