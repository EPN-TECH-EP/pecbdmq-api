package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;
import epntech.cbdmq.pe.dominio.util.DocsUtil;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PeriodoAcademicoService {
	
	PeriodoAcademico save(PeriodoAcademico obj) throws DataException;
	
	List<PeriodoAcademico> getAll();
	
	Optional<PeriodoAcademico> getById(int id);
	
	PeriodoAcademico update(PeriodoAcademico objActualizado) throws DataException;
	
	void deleteById(int id) throws DataException;
	
	List<PeriodoAcademicoSemestreModulo> getAllPeriodoAcademico();
	
	String getEstado();
	
	Integer updateNextState(Integer id, String proceso);
	
	Integer validState(Integer id, String proceso);
	
	Set<Documento> getDocumentos();
	
	Optional<PeriodoAcademico> getActive();
	
	Integer getPAActivo();
	
	List<DocumentoRuta> cargarDocs(List<MultipartFile> archivos)  throws IOException, ArchivoMuyGrandeExcepcion, DataException;
	
	void eliminar(List<DocsUtil> docs);
}
