package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Paralelo;

public interface ParaleloRepository extends JpaRepository<Paralelo, Integer>{
	Optional<Paralelo> findByNombreParaleloIgnoreCase(String Nombre);

}
