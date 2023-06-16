package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Notas;


public interface NotaRepository extends JpaRepository<Notas, Integer>{

	Optional<Notas> findByUsuarioCreaNota(String descripcion);
	
	
	
	
}
