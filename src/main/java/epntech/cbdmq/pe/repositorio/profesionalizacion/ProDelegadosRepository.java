package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProDelegados;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProDelegadosRepository extends JpaRepository<ProDelegados, Integer>, ProfesionalizacionRepository<ProDelegados, Integer> {

    Optional<ProDelegados> findByCodUsuario(int codUsuario);


}
