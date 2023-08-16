package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;


import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProNotasProfesionalizacionDatosRepository extends JpaRepository<ProNotasProfesionalizacionDto, Integer> {

    @Query(value = "select p.cod_nota_profesionalizacion, p.cod_estudiante_semestre_materia_paralelo, pp.nombre_paralelo, ge.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales," +
            "p.nota_parcial1, p.nota_parcial2, p.nota_practica, p.nota_asistencia, p.cod_instructor, p.cod_materia, p.cod_semestre, p.nota_minima, p.peso_materia, p.numero_horas, nota_materia, p.nota_ponderacion, p.nota_disciplina, p.nota_supletorio, p3.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, pcp.nombre_catalogo nombre_proyecto " +
            "from cbdmq.pro_nota_profesionalizacion p " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = p.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo ppsmp on ppsmp.cod_semestre_materia_paralelo = p.cod_estudiante_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on ppsmp.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on ppsmp.cod_proyecto = pcp.cod_catalogo_proyecto " +
            "inner join cbdmq.pro_semestre ps on p.cod_semestre=ps.cod_semestre " +
            "inner join cbdmq.pro_materia pm on p.cod_materia=pm.cod_materia " +
            "inner join cbdmq.pro_materia_semestre p2 on ppsmp.cod_semestre_materia = p2.cod_materia_semestre " +
            "inner join cbdmq.pro_periodo_semestre pps on p2.cod_periodo_semestre = pps.cod_periodo_semestre " +
            "inner join cbdmq.pro_periodo p3 on pps.cod_periodo = p3.cod_periodo " +
            "where ppsmp.cod_semestre_materia_paralelo=:codMateriaParalelo ", nativeQuery = true)
    List<ProNotasProfesionalizacionDto> findByMateriaParalelo(Integer codMateriaParalelo);

    @Query(value = "select p.cod_nota_profesionalizacion, p.cod_estudiante_semestre_materia_paralelo, pp.nombre_paralelo, ge.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales," +
            "p.nota_parcial1, p.nota_parcial2, p.nota_practica, p.nota_asistencia, p.cod_instructor, p.cod_materia, p.cod_semestre, p.nota_minima, p.peso_materia, p.numero_horas, nota_materia, p.nota_ponderacion, p.nota_disciplina, p.nota_supletorio, p3.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, pcp.nombre_catalogo nombre_proyecto " +
            "from cbdmq.pro_nota_profesionalizacion p " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = p.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo ppsmp on ppsmp.cod_semestre_materia_paralelo = p.cod_estudiante_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on ppsmp.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on ppsmp.cod_proyecto = pcp.cod_catalogo_proyecto " +
            "inner join cbdmq.pro_semestre ps on p.cod_semestre=ps.cod_semestre " +
            "inner join cbdmq.pro_materia pm on p.cod_materia=pm.cod_materia " +
            "inner join cbdmq.pro_materia_semestre p2 on ppsmp.cod_semestre_materia = p2.cod_materia_semestre " +
            "inner join cbdmq.pro_periodo_semestre pps on p2.cod_periodo_semestre = pps.cod_periodo_semestre " +
            "inner join cbdmq.pro_periodo p3 on pps.cod_periodo = p3.cod_periodo " +
            "where p.estado='ACTIVO' ", nativeQuery = true)
    List<ProNotasProfesionalizacionDto> getByAll();

    @Query(value = "select p.cod_nota_profesionalizacion, p.cod_estudiante_semestre_materia_paralelo, pp.nombre_paralelo, ge.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales," +
            "p.nota_parcial1, p.nota_parcial2, p.nota_practica, p.nota_asistencia, p.cod_instructor, p.cod_materia, p.cod_semestre, p.nota_minima, p.peso_materia, p.numero_horas, nota_materia, p.nota_ponderacion, p.nota_disciplina, p.nota_supletorio, p3.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, pcp.nombre_catalogo nombre_proyecto " +
            "from cbdmq.pro_nota_profesionalizacion p " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = p.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo ppsmp on ppsmp.cod_semestre_materia_paralelo = p.cod_estudiante_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on ppsmp.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on ppsmp.cod_proyecto = pcp.cod_catalogo_proyecto " +
            "inner join cbdmq.pro_semestre ps on p.cod_semestre=ps.cod_semestre " +
            "inner join cbdmq.pro_materia pm on p.cod_materia=pm.cod_materia " +
            "inner join cbdmq.pro_materia_semestre p2 on ppsmp.cod_semestre_materia = p2.cod_materia_semestre " +
            "inner join cbdmq.pro_periodo_semestre pps on p2.cod_periodo_semestre = pps.cod_periodo_semestre " +
            "inner join cbdmq.pro_periodo p3 on pps.cod_periodo = p3.cod_periodo " +
            "where p3.cod_periodo=:codPeriodo and p.estado='ACTIVO'", nativeQuery = true)
    List<ProNotasProfesionalizacionDto> getAllByCodPeriodo(Integer codPeriodo);

    @Query(value = "select p.cod_nota_profesionalizacion, p.cod_estudiante_semestre_materia_paralelo, pp.nombre_paralelo, ge.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales," +
            "p.nota_parcial1, p.nota_parcial2, p.nota_practica, p.nota_asistencia, p.cod_instructor, p.cod_materia, p.cod_semestre, p.nota_minima, p.peso_materia, p.numero_horas, nota_materia, p.nota_ponderacion, p.nota_disciplina, p.nota_supletorio, p3.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, pcp.nombre_catalogo nombre_proyecto " +
            "from cbdmq.pro_nota_profesionalizacion p " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = p.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo ppsmp on ppsmp.cod_semestre_materia_paralelo = p.cod_estudiante_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on ppsmp.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on ppsmp.cod_proyecto = pcp.cod_catalogo_proyecto " +
            "inner join cbdmq.pro_semestre ps on p.cod_semestre=ps.cod_semestre " +
            "inner join cbdmq.pro_materia pm on p.cod_materia=pm.cod_materia " +
            "inner join cbdmq.pro_materia_semestre p2 on ppsmp.cod_semestre_materia = p2.cod_materia_semestre " +
            "inner join cbdmq.pro_periodo_semestre pps on p2.cod_periodo_semestre = pps.cod_periodo_semestre " +
            "inner join cbdmq.pro_periodo p3 on pps.cod_periodo = p3.cod_periodo " +
            "where p.cod_semestre=:codSemestre and p.estado='ACTIVO'", nativeQuery = true)
    List<ProNotasProfesionalizacionDto> getAllByCodSemestre(Integer codSemestre);

    @Query(value = "select p.cod_nota_profesionalizacion, p.cod_estudiante_semestre_materia_paralelo, pp.nombre_paralelo, ge.cod_estudiante, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales," +
            "p.nota_parcial1, p.nota_parcial2, p.nota_practica, p.nota_asistencia, p.cod_instructor, p.cod_materia, p.cod_semestre, p.nota_minima, p.peso_materia, p.numero_horas, nota_materia, p.nota_ponderacion, p.nota_disciplina, p.nota_supletorio, p3.nombre_periodo, ps.semestre nombre_semestre, pm.nombre_materia, pcp.nombre_catalogo nombre_proyecto " +
            "from cbdmq.pro_nota_profesionalizacion p " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = p.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales " +
            "inner join cbdmq.pro_periodo_semestre_materia_paralelo ppsmp on ppsmp.cod_semestre_materia_paralelo = p.cod_estudiante_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo pp on ppsmp.cod_paralelo = pp.cod_paralelo " +
            "left join cbdmq.pro_catalogo_proyecto pcp on ppsmp.cod_proyecto = pcp.cod_catalogo_proyecto " +
            "inner join cbdmq.pro_semestre ps on p.cod_semestre=ps.cod_semestre " +
            "inner join cbdmq.pro_materia pm on p.cod_materia=pm.cod_materia " +
            "inner join cbdmq.pro_materia_semestre p2 on ppsmp.cod_semestre_materia = p2.cod_materia_semestre " +
            "inner join cbdmq.pro_periodo_semestre pps on p2.cod_periodo_semestre = pps.cod_periodo_semestre " +
            "inner join cbdmq.pro_periodo p3 on pps.cod_periodo = p3.cod_periodo " +
            "where p.cod_semestre=:codSemestre and p3.cod_periodo=:codPeriodo and p.estado='ACTIVO'", nativeQuery = true)
    List<ProNotasProfesionalizacionDto> getAllByCodSemestreAndCodPeriodo(Integer codSemestre, Integer codPeriodo);
}