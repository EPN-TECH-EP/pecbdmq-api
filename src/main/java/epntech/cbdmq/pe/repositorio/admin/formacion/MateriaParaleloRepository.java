package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MateriaParaleloRepository extends JpaRepository<MateriaParalelo,Integer> {
    Optional<MateriaParalelo> findByCodMateriaPeriodoAndCodParalelo(Integer materiaParalelo, Integer paralelo);

    @Query(value="select mp.* from cbdmq.gen_materia_paralelo mp \n" +
            "left join cbdmq.gen_materia_periodo pe on mp.cod_materia_periodo = pe.cod_materia_periodo \n" +
            "left join cbdmq.gen_instructor_materia_paralelo gimp on mp.cod_materia_paralelo = gimp.cod_materia_paralelo \n" +
            "left join cbdmq.gen_tipo_instructor gti on gimp.cod_tipo_instructor = gti.cod_tipo_instructor \n" +
            "left join cbdmq.gen_instructor gi on gimp.cod_instructor = gi.cod_instructor\n" +
            "left join cbdmq.gen_dato_personal gdp on gi.cod_datos_personales = gi.cod_datos_personales \n" +
            "left join cbdmq.gen_usuario gu on gdp.cod_datos_personales = gu.cod_datos_personales\n" +
            "where gu.cod_usuario = :codUsuario and gti.nombre_tipo_instructor =:tipo and pe.cod_periodo_academico = cbdmq.get_pa_activo()",nativeQuery = true)
    List<MateriaParalelo> getMateriaParaleloByUser(@Param("codUsuario") Integer User, @Param("tipo") String tipo);
}
