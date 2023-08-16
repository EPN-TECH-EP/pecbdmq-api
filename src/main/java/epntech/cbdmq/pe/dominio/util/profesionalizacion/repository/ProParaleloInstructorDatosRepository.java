package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloInstructorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProParaleloInstructorDatosRepository extends JpaRepository<ProParaleloInstructorDto, Integer> {

    @Query(value = "select ms.cod_periodo_semestre_materia_paralelo_instructor, ms.cod_periodo_semestre_materia_paralelo, ms.cod_instructor, ps.nombre_paralelo , dp.nombre, dp.apellido, dp.correo_personal, dp.cod_datos_personales, p2.nombre_catalogo  " +
            "from cbdmq.pro_periodo_semestre_materia_paralelo_instructor ms inner join cbdmq.pro_periodo_semestre_materia_paralelo pe on pe.cod_semestre_materia_paralelo = ms.cod_periodo_semestre_materia_paralelo " +
            "left join cbdmq.pro_paralelo ps on ps.cod_paralelo = pe.cod_paralelo left join cbdmq.pro_catalogo_proyecto p2 on p2.cod_catalogo_proyecto=pe.cod_proyecto" +
            " join cbdmq.pro_instructor pm on ms.cod_instructor = pm.cod_instructor " +
            "inner join cbdmq.gen_dato_personal dp on dp.cod_datos_personales = pm.cod_datos_personales " +
            "where ms.cod_periodo_semestre_materia_paralelo =:codigo and ms.estado='ACTIVO'", nativeQuery = true)
    List<ProParaleloInstructorDto> getAllByCodMateriaParalelo(Integer codigo);
}
