package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionFinalDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProNotasProfesionalizacionFinalDatosRepository extends JpaRepository<ProNotasProfesionalizacionFinalDto, Integer> {

    @Query(value = "select p.cod_nota_profesionalizacion_final, p.cod_estudiante, p.cod_semestre, p.promedio_disciplina_instructor, " +
            "p.promedio_disciplina_oficial_semana, p.promedio_academico, p.promedio_disciplina_final, p.ponderacion_academica, p.ponderacion_disciplina, " +
            "p.nota_final_academica, p.nota_final_disciplina, p.nota_final, p.realizo_encuesta, p.aprobado, p.nota_parcial1, p.nota_parcial2, " +
            "p.nota_practica, p.nota_asistencia, p.cod_periodo, gdp.nombre, gdp.apellido, gdp.correo_personal, gdp.cod_datos_personales " +
            "from cbdmq.pro_nota_profesionalizacion_final p " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = p.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales " +
            "inner join cbdmq.pro_periodo pp on p.cod_periodo = pp.cod_periodo " +
            "inner join cbdmq.pro_semestre ps on p.cod_semestre = ps.cod_semestre " +
            "where p.cod_periodo=:codPeriodo and p.cod_semestre=:codSemestre", nativeQuery = true)
    List<ProNotasProfesionalizacionFinalDto> findByCodPeriodoAndCodSemestre(Integer codPeriodo, Integer codSemestre);
}
