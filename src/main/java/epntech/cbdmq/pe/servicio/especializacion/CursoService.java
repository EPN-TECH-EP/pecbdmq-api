package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CursoService {
	
	Curso save(Curso obj, Set<Requisito> requisitos, List<MultipartFile> documentos, Long codTipoDocumento);
	
	Curso update(Curso objActualizado);
	
	List<Curso> listarAll();
	
	Optional<Curso> getById(Long id);
	
	CursoDocumento updateEstadoAprobadoValidado(Boolean estadoAprobado, Boolean estadoValidado, String observaciones, Long codCursoEspecializacion, Long codDocumento);

	Curso updateEstadoProceso(Long estado, Long codCurso) throws DataException;
	
	Curso updateRequisitos(Long codCursoEspecializacion, List<Requisito> requisitos);
	
	Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException;
	
	Optional<Curso> uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos, Long codTipoDocumento)  throws IOException, ArchivoMuyGrandeExcepcion ;
	
	void delete(Long codCursoEspecializacion);
}
