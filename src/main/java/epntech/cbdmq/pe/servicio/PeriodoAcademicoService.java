package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
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

    Set<Documento> getDocumentosPActive();

    Optional<PeriodoAcademico> getActive();

    Integer getPAActivo();


    void cargarDocs(List<MultipartFile> archivos, String descripcion, String observacion) throws IOException, ArchivoMuyGrandeExcepcion, DataException;


    void eliminar(List<DocsUtil> docs);

    Boolean cerrarPeriodoAcademico() throws ParseException;
    public Set<Documento> getDocumentosByPeriodo(Integer codPA);
}
