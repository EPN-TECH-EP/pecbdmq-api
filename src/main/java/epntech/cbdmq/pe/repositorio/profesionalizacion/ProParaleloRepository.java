package epntech.cbdmq.pe.repositorio.profesionalizacion;


import epntech.cbdmq.pe.dominio.profesionalizacion.ProParalelo;

import java.util.Optional;

public interface ProParaleloRepository extends ProfesionalizacionRepository<ProParalelo, Integer> {

    Optional<ProParalelo> findByNombreParaleloIgnoreCase(String nombreParalelo);
}
