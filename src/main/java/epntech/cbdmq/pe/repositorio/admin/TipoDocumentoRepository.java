package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

	Optional<TipoDocumento> findByTipoDocumento(String nombre);
}
