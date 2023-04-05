package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.ModuloEstados;

public interface ModuloEstadosRepository extends JpaRepository<ModuloEstados, Integer> {
	
	List<ModuloEstados> findByModuloAndEstadoCatalogo(Integer modulo, Integer catalgo);
}
