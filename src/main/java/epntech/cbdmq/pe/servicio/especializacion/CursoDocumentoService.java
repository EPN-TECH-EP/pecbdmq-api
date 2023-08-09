package epntech.cbdmq.pe.servicio.especializacion;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CursoDocumentoService {
    Set<Documento> getDocumentos(Long codCurso) throws IOException;
    Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException;
    public Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException;
    void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion)
            throws IOException, ArchivoMuyGrandeExcepcion, DataException;
    void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) throws DataException, IOException;
    void generaDocumento(String ruta, String nombre, Long codCursoEspecializacion)throws DataException ;
    Set<Documento> getDocumentosByCurso(Long codigoCurso);
    void generarDocumentoInscritos(HttpServletResponse response,Long codigoCurso);
    void generarExcel(String filePath, String nombre, Long codCurso, String estado) throws IOException, DataException;

    void generarPDF(HttpServletResponse response, String filePath, String nombre, Long codCurso, String estado)
            throws DocumentException, IOException, DataException;
    Boolean generarDocListadoGeneral(HttpServletResponse response,  Long codCurso, String estado);
    public Boolean generarDocListadoInscripcion(HttpServletResponse response,Long codCurso);
    public Boolean generarDocListadoValidacion(HttpServletResponse response,Long codCurso);
    public Boolean generarDocListadoPruebas(HttpServletResponse response,Long codCurso);

}
