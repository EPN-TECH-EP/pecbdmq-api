package epntech.cbdmq.pe.repositorio.admin.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.NotasEspecializacion;
import epntech.cbdmq.pe.dto.especializacion.NotasEspecializacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotasEspecializacionRepository extends JpaRepository<NotasEspecializacion, Integer> {

    @Query(nativeQuery = true, name = "NotasEspecializacion.findNotasCurso")
    List<NotasEspecializacionDTO> findNotasCurso(@Param("codCurso") Integer codCurso);

    @Query(nativeQuery = true, name = "NotasEspecializacion.findAprobadosCurso")
    List<NotasEspecializacionDTO> findAprobadosCurso(@Param("codCurso") Integer codCurso);

    Optional<NotasEspecializacion> findByCodInscripcion(Integer codInscripcion);

}

