package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.EspecializacionConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.dominio.util.ModuloEstadosData;
import epntech.cbdmq.pe.repositorio.admin.EstadosRepository;
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoEstadoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoEstadoService;

@Service
public class CursoEstadoServiceImpl implements CursoEstadoService {

    @Autowired
    private CursoEstadoRepository cursoEstadoRepository;
    @Autowired
    private EstadosRepository estadoRepository;
    /*
    @Autowired
    private InscripcionEspService inscripcionEspSvc;

     */

    @Override
    public CursoEstado save(CursoEstado cursoEstado) throws DataException {
        Optional<CursoEstado> cursoEstadoOptional;

        cursoEstadoOptional = cursoEstadoRepository.getByCursoYCatalogoEstado(cursoEstado.getCodTipoCurso(), cursoEstado.getCodCatalogoEstados());
        if (cursoEstadoOptional.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        cursoEstadoOptional = cursoEstadoRepository.getByCursoYOrden(cursoEstado.getCodTipoCurso(), cursoEstado.getOrden());
        if (cursoEstadoOptional.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);

        return cursoEstadoRepository.save(cursoEstado);
    }

    @Override
    public CursoEstado update(CursoEstado cursoEstadoActualizado) throws DataException {
        Optional<CursoEstado> cursoEstadoOptional;
        cursoEstadoOptional = cursoEstadoRepository.findById(cursoEstadoActualizado.getCodCursoEstado());
        if (cursoEstadoOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        cursoEstadoOptional = cursoEstadoRepository.getByCursoYCatalogoEstado(cursoEstadoActualizado.getCodTipoCurso(), cursoEstadoActualizado.getCodCatalogoEstados());

        if (cursoEstadoOptional.isPresent() && !cursoEstadoOptional.get().getCodCursoEstado().equals(cursoEstadoActualizado.getCodCursoEstado()))
            throw new DataException(REGISTRO_YA_EXISTE);
        cursoEstadoOptional = cursoEstadoRepository.getByCursoYOrden(cursoEstadoActualizado.getCodTipoCurso(), cursoEstadoActualizado.getOrden());
        if (cursoEstadoOptional.isPresent() && !cursoEstadoOptional.get().getCodCursoEstado().equals(cursoEstadoActualizado.getCodCursoEstado()))
            throw new DataException(REGISTRO_YA_EXISTE);

        return cursoEstadoRepository.save(cursoEstadoActualizado);
    }

    @Override
    public List<CursoEstado> listarTodo() {
        // TODO Auto-generated method stub
        return cursoEstadoRepository.findAll();
    }

    @Override
    public List<CursoEstado> listarByTipoCurso(Long codTipoCurso) throws DataException {
        return cursoEstadoRepository.findAllByCodTipoCurso(codTipoCurso);
    }

    @Override
    public List<ModuloEstadosData> listarModuloEstadosByTipoCurso(Long codTipoCurso) throws DataException {
        List<CursoEstado> lista = this.listarByTipoCurso(codTipoCurso);
        List<ModuloEstadosData> listaII = lista.stream().map(cursoEstado ->
        {
            ModuloEstadosData moduloEstadosObj = new ModuloEstadosData();
            moduloEstadosObj.setCodigo(cursoEstado.getCodCursoEstado().intValue());
            Estados estado = estadoRepository.findById(cursoEstado.getCodCatalogoEstados().intValue()).orElse(null);
            moduloEstadosObj.setEstadoCatalogo(estado.getNombre());
            moduloEstadosObj.setOrden(cursoEstado.getOrden());
            moduloEstadosObj.setEstado(cursoEstado.getEstado());
            return moduloEstadosObj;

        }).toList();
        return listaII;
    }

    @Override
    public List<Estados> listarEstadosByTipoCurso(Long codTipoCurso) throws DataException {
        List<Estados> lista = this.listarByTipoCurso(codTipoCurso).stream().map(cursoEstado ->
        {
            Estados estados = estadoRepository.findById(cursoEstado.getCodCatalogoEstados().intValue()).orElse(null);
            return estados;
        }).toList();
        return lista;
    }

    @Override
    public CursoEstado getByTipoCursoAndEstado(Long codTipoCurso, String estado) throws DataException {
        return cursoEstadoRepository.getEstadoByCurso(codTipoCurso, estado);
    }

    @Override
    public Optional<CursoEstado> getById(Long codCursoEstado) throws DataException {
        Optional<CursoEstado> cursoEstadoOptional;
        cursoEstadoOptional = cursoEstadoRepository.findById(codCursoEstado);
        if (cursoEstadoOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        return cursoEstadoRepository.findById(codCursoEstado);
    }

    @Override
    public void delete(Long codCursoEstado) throws DataException {
        Optional<CursoEstado> cursoEstadoOptional;
        cursoEstadoOptional = cursoEstadoRepository.findById(codCursoEstado);
        if (cursoEstadoOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        cursoEstadoRepository.deleteById(codCursoEstado);

    }

    @Override
    public String getEstadoByCurso(Long codCurso) {
        return cursoEstadoRepository.getEstadoByCurso(codCurso);
    }

    @Override
    public String updateState(Integer idCurso, Integer idCursoEstado) {
        String mensaje = cursoEstadoRepository.updateState(idCurso, idCursoEstado);
        /**/

        return mensaje;
    }

    @Override
    public Integer updateNextState(Integer idCurso) {
        return cursoEstadoRepository.updateNextState(idCurso);
    }

    @Override
    public String previousState(Integer id) {
        return cursoEstadoRepository.getBeforeState(id);
    }


}
