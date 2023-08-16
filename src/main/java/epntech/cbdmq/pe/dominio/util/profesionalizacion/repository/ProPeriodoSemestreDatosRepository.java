package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoSemestreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProPeriodoSemestreDatosRepository extends JpaRepository<ProPeriodoSemestreDto, Integer> {

    @Query(value = "select ps.cod_periodo_semestre, ps.cod_periodo, ps.cod_semestre, p.nombre_periodo, s.semestre nombre_semestre from cbdmq.pro_periodo_semestre ps inner join cbdmq.pro_semestre s on ps.cod_semestre= s.cod_semestre inner join cbdmq.pro_periodo p on ps.cod_periodo=p.cod_periodo where ps.estado='ACTIVO' and ps.cod_periodo=:codSemestre", nativeQuery = true)
    List<ProPeriodoSemestreDto> getAllByCodPeriodo(Integer codSemestre);
}
