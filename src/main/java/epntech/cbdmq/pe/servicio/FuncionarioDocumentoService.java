package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.llamamiento.FuncionarioDocumento;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.CursoDocumentoEstudiante;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FuncionarioDocumentoService {
    List<FuncionarioDocumento> getAll();
    Optional<FuncionarioDocumento> getById(Integer id);
    List <FuncionarioDocumento> getAllReconocimiento();
    List <FuncionarioDocumento> getAllSancion();
    List <FuncionarioDocumento> getByFuncionarioReconocimiento(Integer codFuncionario);
    List <FuncionarioDocumento> getByFuncionarioSancion(Integer codFuncionario);
    FuncionarioDocumento saveOrUpdate(FuncionarioDocumento funcionarioDocumento);
    FuncionarioDocumento saveConArchivo(FuncionarioDocumento funcionarioDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException;
    void deleteFuncionarioDocumento(Integer id);
}
