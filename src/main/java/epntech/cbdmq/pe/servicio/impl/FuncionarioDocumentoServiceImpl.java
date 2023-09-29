package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.llamamiento.FuncionarioDocumento;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.CursoDocumentoEstudiante;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.FuncionarioDocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;
import epntech.cbdmq.pe.servicio.FuncionarioDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;

@Service
public class FuncionarioDocumentoServiceImpl implements FuncionarioDocumentoService {
    @Autowired
    private FuncionarioDocumentoRepository repo;
    @Autowired
    private DocumentoService documentoService;
    @Override
    public List<FuncionarioDocumento> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<FuncionarioDocumento> getById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<FuncionarioDocumento> getAllReconocimiento() {
        return repo.findByEsReconocimiento(true);
    }

    @Override
    public List<FuncionarioDocumento> getAllSancion() {
        return repo.findByEsSancion(true);
    }

    @Override
    public List<FuncionarioDocumento> getByFuncionarioReconocimiento(Integer codFuncionario) {
        return repo.findByCodFuncionarioAndEsReconocimiento(codFuncionario, true);
    }

    @Override
    public List<FuncionarioDocumento> getByFuncionarioSancion(Integer codFuncionario) {
        return repo.findByCodFuncionarioAndEsSancion(codFuncionario, true);
    }

    @Override
    public FuncionarioDocumento saveOrUpdate(FuncionarioDocumento funcionarioDocumento) {
        return repo.save(funcionarioDocumento);
    }

    @Override
    public FuncionarioDocumento saveConArchivo(FuncionarioDocumento funcionarioDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException {
        List<Integer> idsDocumento = documentoService.guardarArchivoCompleto("LLAMAMIENTO", funcionarioDocumento.getCodFuncionario().toString(), archivos);
        FuncionarioDocumento funcionario = null;
        if (idsDocumento.size() > 0) {
            for (Integer idDocumento : idsDocumento) {
                funcionarioDocumento.setCodDocumento(idDocumento);
                funcionario = repo.save(funcionarioDocumento);
            }
        }
        return funcionario;
    }

    @Override
    public void deleteFuncionarioDocumento(Integer id) {
        FuncionarioDocumento funcionarioDocumento = repo.findById(id).get();
        if (funcionarioDocumento.getCodDocumento() != null) {
            repo.deleteById(id);
            documentoService.delete(funcionarioDocumento.getCodDocumento());
        }

    }
}
