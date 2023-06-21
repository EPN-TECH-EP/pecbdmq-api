package epntech.cbdmq.pe.repositorio.admin.especializacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionDocumento;
import jakarta.transaction.Transactional;

public interface InscripcionDocumentoRepository extends JpaRepository<InscripcionDocumento, Long> {

	@Transactional
	@Modifying
	@Query("delete from InscripcionDocumento a where a.codInscripcion = ?1 and codDocumento = ?2")
	void deleteByCodInscripcionAndCodDocumento(Long codInscripcion, Long codDocumento);
}
