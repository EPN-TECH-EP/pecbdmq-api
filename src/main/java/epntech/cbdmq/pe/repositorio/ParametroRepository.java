package epntech.cbdmq.pe.repositorio;

import java.util.Optional;

import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import epntech.cbdmq.pe.dominio.Parametro;

public interface ParametroRepository extends ProfesionalizacionRepository<Parametro, Long> {

	Optional<Parametro> findByNombreParametro(String nombre);
}
