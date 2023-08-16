package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProCumpleRequisitosDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProValidaCumpleRequisitoRepository extends JpaRepository<ProCumpleRequisitosDto, Integer> {
    @Query(value = "select pcr.cod_cumple_requisito, pcr.cod_inscripcion, pcr.cod_requisito, pcr2.nombre_requisito, pcr.cumple, pcr.observaciones, pcr.observacion_muestra  from cbdmq.pro_cumple_requisitos pcr " +
            "inner join cbdmq.gen_requisito pcr2 on pcr.cod_requisito= pcr2.cod_requisito " +
            "where pcr.cod_inscripcion =:codInscripcion", nativeQuery = true)
    List<ProCumpleRequisitosDto> findRequisitosByInscripcion(Integer codInscripcion);
}
