package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.admin.llamamiento.FuncionarioDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FuncionarioDocumentoRepository extends JpaRepository<FuncionarioDocumento, Integer> {
    List<FuncionarioDocumento> findByEsReconocimiento(Boolean esReconocimiento);
    List<FuncionarioDocumento> findByEsSancion(Boolean esSancion);
    List<FuncionarioDocumento> findByCodFuncionarioAndEsReconocimiento(Integer codFuncionario, Boolean esReconocimiento);
    List<FuncionarioDocumento> findByCodFuncionarioAndEsSancion(Integer codFuncionario, Boolean esSancion);

}
