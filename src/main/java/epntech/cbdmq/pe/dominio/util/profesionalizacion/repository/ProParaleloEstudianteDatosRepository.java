package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloEstudianteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProParaleloEstudianteDatosRepository extends JpaRepository<ProParaleloEstudianteDto, Integer> {

    @Query(value = "select ppsmp.cod_paralelo_estudiante, ppsmp.cod_semestre_materia_paralelo cod_materia_paralelo, pp.nombre_paralelo, ppsmp.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales, gdp.cedula, pms.cod_proyecto, pcp.nombre_catalogo  from cbdmq.pro_periodo_semestre_materia_paralelo_estudiante ppsmp " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo pms on pms.cod_semestre_materia_paralelo = ppsmp.cod_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on pms.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on pcp.cod_catalogo_proyecto = pms.cod_proyecto " +
            "left join cbdmq.gen_estudiante ge on ge.cod_estudiante = ppsmp.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on ge.cod_datos_personales = gdp.cod_datos_personales " +
            "where ppsmp.cod_semestre_materia_paralelo=:codMateriaParalelo and ppsmp.estado='ACTIVO'", nativeQuery = true)
    List<ProParaleloEstudianteDto> findByMateriaParalelo(Integer codMateriaParalelo);

    @Query(value = "select ppsmp.cod_paralelo_estudiante, ppsmp.cod_semestre_materia_paralelo cod_materia_paralelo, pp.nombre_paralelo, ppsmp.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales, gdp.cedula, pms.cod_proyecto, pcp.nombre_catalogo  from cbdmq.pro_periodo_semestre_materia_paralelo_estudiante ppsmp " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo pms on pms.cod_semestre_materia_paralelo = ppsmp.cod_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on pms.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on pcp.cod_catalogo_proyecto = pms.cod_proyecto " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = ppsmp.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on ge.cod_datos_personales = gdp.cod_datos_personales " +
            "inner join cbdmq.pro_periodo_estudiante_semestre_materia ppesm on pms.cod_semestre_materia = ppesm.cod_periodo_estudiante_semestre " +
            "inner join cbdmq.pro_periodo_semestre pps on ppesm.cod_periodo_estudiante_semestre = pps.cod_periodo_semestre " +
            "where pps.cod_semestre=:codSemestre and pps.cod_periodo=:codPeriodo and ppsmp.estado='ACTIVO'", nativeQuery = true)
    List<ProParaleloEstudianteDto> findByCodPeriodoAndCodSemestre(Integer codPeriodo, Integer codSemestre);
}
