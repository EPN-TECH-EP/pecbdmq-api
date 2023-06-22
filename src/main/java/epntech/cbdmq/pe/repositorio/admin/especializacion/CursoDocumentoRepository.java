package epntech.cbdmq.pe.repositorio.admin.especializacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import jakarta.transaction.Transactional;

public interface CursoDocumentoRepository extends JpaRepository<CursoDocumento, Long> {

	@Transactional
	@Modifying
	@Query("update CursoDocumento a set a.aprobado = ?1, a.validado = ?2, a.observaciones = ?3 where a.codCursoEspecializacion = ?4 and a.codDocumento = ?5")
	void updateEstadoAprobado(Boolean estadoAprobado, Boolean estadoValidado, String observaciones, Long codCursoEspecializacion, Long codDocumento);
	
	CursoDocumento findByCodCursoEspecializacionAndCodDocumento(Long codCursoEspecializacion, Long codDocumento);
	
	@Procedure(name = "cbdmq.valida_documentos_curso_especializacion")
    void validaDocumentosCurso(Long codCursoEspecializacion);
	
}
