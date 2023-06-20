package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import epntech.cbdmq.pe.dominio.admin.especializacion.ModuloEspecializacion;

public interface ModuloEspecializacionRepository extends JpaRepository<ModuloEspecializacion, Long> {

	Optional<ModuloEspecializacion> findByNombreEspModuloIgnoreCase(String Nombre);
}
