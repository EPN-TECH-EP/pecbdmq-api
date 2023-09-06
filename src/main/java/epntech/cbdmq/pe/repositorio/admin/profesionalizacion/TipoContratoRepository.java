package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.TipoContrato;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import java.util.Optional;

public interface TipoContratoRepository extends ProfesionalizacionRepository<TipoContrato, Integer> {

    Optional<TipoContrato> findByNombre(String Nombre);
 }
