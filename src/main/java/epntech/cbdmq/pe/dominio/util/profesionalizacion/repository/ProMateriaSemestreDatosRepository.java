package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaSemestreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProMateriaSemestreDatosRepository extends JpaRepository<ProMateriaSemestreDto, Integer> {

    @Query(value = "select ms.cod_materia_semestre, ms.cod_periodo_semestre, ms.cod_materia, pe.cod_periodo, pe.cod_semestre, ms.cod_aula, " +
            "pp.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, ga.nombre_aula, ms.numero_horas, ms.nota_minima, ms.nota_maxima, " +
            "ms.asistencia_minima, pm.es_proyecto  " +
            "from cbdmq.pro_materia_semestre ms inner join cbdmq.pro_periodo_semestre pe on pe.cod_periodo_semestre = ms.cod_periodo_semestre " +
            "inner join cbdmq.pro_semestre ps on ps.cod_semestre = pe.cod_semestre " +
            "inner join cbdmq.pro_materia pm on ms.cod_materia = pm.cod_materia " +
            "inner join cbdmq.pro_periodo pp on pe.cod_periodo = pp.cod_periodo " +
            "inner join cbdmq.gen_aula ga on ms.cod_aula = ga.cod_aula " +
            "where ms.cod_periodo_semestre =:codigo and ms.estado='ACTIVO'", nativeQuery = true)
    List<ProMateriaSemestreDto> getAllByCodMateriaSemestre(Integer codigo);

    @Query(value = "select ms.cod_materia_semestre, ms.cod_periodo_semestre, ms.cod_materia, pe.cod_periodo, pe.cod_semestre, ms.cod_aula, " +
            "pp.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, ga.nombre_aula, ms.numero_horas, ms.nota_minima, ms.nota_maxima,ms.asistencia_minima, pm.es_proyecto  " +
            "from cbdmq.pro_materia_semestre ms inner join cbdmq.pro_periodo_semestre pe on pe.cod_periodo_semestre = ms.cod_periodo_semestre " +
            "inner join cbdmq.pro_semestre ps on ps.cod_semestre = pe.cod_semestre " +
            "inner join cbdmq.pro_materia pm on ms.cod_materia = pm.cod_materia " +
            "inner join cbdmq.pro_periodo pp on pe.cod_periodo = pp.cod_periodo " +
            "inner join cbdmq.gen_aula ga on ms.cod_aula = ga.cod_aula " +
            "where ps.cod_semestre =:codigo and ms.estado='ACTIVO' and pp.cod_periodo=:codPeriodo", nativeQuery = true)
    List<ProMateriaSemestreDto> getAllByCodSemestreAndCodPeriodo(Integer codigo, Integer codPeriodo);

    @Query(value = "select ms.cod_materia_semestre, ms.cod_periodo_semestre, ms.cod_materia, pe.cod_periodo, pe.cod_semestre, ms.cod_aula, " +
            "pp.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, ga.nombre_aula, ms.numero_horas, ms.nota_minima, ms.nota_maxima,ms.asistencia_minima, pm.es_proyecto  " +
            "from cbdmq.pro_materia_semestre ms inner join cbdmq.pro_periodo_semestre pe on pe.cod_periodo_semestre = ms.cod_periodo_semestre " +
            "inner join cbdmq.pro_semestre ps on ps.cod_semestre = pe.cod_semestre " +
            "inner join cbdmq.pro_materia pm on ms.cod_materia = pm.cod_materia " +
            "inner join cbdmq.pro_periodo pp on pe.cod_periodo = pp.cod_periodo " +
            "inner join cbdmq.gen_aula ga on ms.cod_aula = ga.cod_aula " +
            "where ms.estado='ACTIVO'", nativeQuery = true)
    List<ProMateriaSemestreDto> getByAll();

    @Query(value = "select ms.cod_materia_semestre, ms.cod_periodo_semestre, ms.cod_materia, pe.cod_periodo, pe.cod_semestre, ms.cod_aula, " +
            "pp.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, ga.nombre_aula, ms.numero_horas, ms.nota_minima, ms.nota_maxima,ms.asistencia_minima, pm.es_proyecto  " +
            "from cbdmq.pro_materia_semestre ms inner join cbdmq.pro_periodo_semestre pe on pe.cod_periodo_semestre = ms.cod_periodo_semestre " +
            "inner join cbdmq.pro_semestre ps on ps.cod_semestre = pe.cod_semestre " +
            "inner join cbdmq.pro_materia pm on ms.cod_materia = pm.cod_materia " +
            "inner join cbdmq.pro_periodo pp on pe.cod_periodo = pp.cod_periodo " +
            "inner join cbdmq.gen_aula ga on ms.cod_aula = ga.cod_aula " +
            "where ps.cod_semestre =:codigo and ms.estado='ACTIVO'", nativeQuery = true)
    List<ProMateriaSemestreDto> getAllByCodSemestre(Integer codigo);

    @Query(value = "select ms.cod_materia_semestre, ms.cod_periodo_semestre, ms.cod_materia, pe.cod_periodo, pe.cod_semestre, ms.cod_aula, " +
            "pp.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, ga.nombre_aula, ms.numero_horas, ms.nota_minima, ms.nota_maxima,ms.asistencia_minima, pm.es_proyecto  " +
            "from cbdmq.pro_materia_semestre ms inner join cbdmq.pro_periodo_semestre pe on pe.cod_periodo_semestre = ms.cod_periodo_semestre " +
            "inner join cbdmq.pro_semestre ps on ps.cod_semestre = pe.cod_semestre " +
            "inner join cbdmq.pro_materia pm on ms.cod_materia = pm.cod_materia " +
            "inner join cbdmq.pro_periodo pp on pe.cod_periodo = pp.cod_periodo " +
            "inner join cbdmq.gen_aula ga on ms.cod_aula = ga.cod_aula " +
            "where ms.estado='ACTIVO' and pp.cod_periodo=:codPeriodo", nativeQuery = true)
    List<ProMateriaSemestreDto> getAllByCodPeriodo( Integer codPeriodo);
}
