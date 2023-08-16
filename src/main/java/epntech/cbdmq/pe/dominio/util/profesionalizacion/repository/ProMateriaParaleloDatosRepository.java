package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaParaleloDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProMateriaParaleloDatosRepository extends JpaRepository<ProMateriaParaleloDto, Integer> {

    @Query(value = "select ms.cod_semestre_materia_paralelo cod_semestre_materia_paralelo, pe.cod_materia_semestre cod_semestre_materia, ms.cod_paralelo, pm.nombre_paralelo , ps.nombre_materia, pp.cod_catalogo_proyecto cod_proyecto, pp.nombre_catalogo nombre_proyecto, ps.es_proyecto " +
            "from cbdmq.pro_periodo_semestre_materia_paralelo ms inner join cbdmq.pro_materia_semestre pe on pe.cod_materia_semestre = ms.cod_semestre_materia " +
            "inner join cbdmq.pro_materia ps on ps.cod_materia = pe.cod_materia " +
            "left join cbdmq.pro_paralelo pm on ms.cod_paralelo = pm.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pp on ms.cod_proyecto = pp.cod_catalogo_proyecto " +
            "where pe.cod_materia_semestre =:codigo and ms.estado='ACTIVO'", nativeQuery = true)
    List<ProMateriaParaleloDto> getAllByCodSemestreMateria(Integer codigo);
}
