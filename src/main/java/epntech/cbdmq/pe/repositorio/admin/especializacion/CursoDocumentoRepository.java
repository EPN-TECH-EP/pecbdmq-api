package epntech.cbdmq.pe.repositorio.admin.especializacion;

import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CursoDocumentoRepository extends JpaRepository<CursoDocumento, Long> {

	@Transactional
	@Modifying
	@Query("update CursoDocumento a set a.aprobado = ?1, a.esTarea = ?2, a.observaciones = ?3 where a.codCursoEspecializacion = ?4 and a.codDocumento = ?5")
	void updateEstadoAprobado(Boolean estadoAprobado, Boolean estadoValidado, String observaciones, Long codCursoEspecializacion, Long codDocumento);
	
	CursoDocumento findByCodCursoEspecializacionAndCodDocumento(Long codCursoEspecializacion, Long codDocumento);
	
	//@Procedure(name = "validaDocumentosCursoEspecializacion")
    //void validaDocumentosCursoEspecializacion(@Param("codCursoEspecializacion") Long codCursoEspecializacion);
	
	@Transactional
	@Modifying
	@Query("delete from CursoDocumento a where a.codCursoEspecializacion = ?1 and codDocumento = ?2")
	void deleteByCodCursoEspecializacionAndCodDocumento(Long codCursoEspecializacion, Long codDocumento);

	List<CursoDocumento> findAllByCodCursoEspecializacion(Long codCursoEspecializacion);
	@Query(name = "CursoDocumentoDto.findByCodCurso", nativeQuery = true)
	List<MateriaCursoDocumentoDto> findByCodCurso(@Param("codCurso") Long codCurso);
}
