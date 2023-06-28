package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InstructorMateriaParaleloRepository extends JpaRepository<InstructorMateriaParalelo,Integer> {

    @Query(value = "SELECT ins.cod_instructor, tp.cod_tipo_procedencia, tp.tipo_procedencia, e.cod_estacion, e.nombre_zona, ug.cod_unidad_gestion, ug.unidad_gestion, tc.cod_tipo_contrato, tc.nombre_tipo_contrato, dp.cedula, dp.nombre, dp.apellido, dp.correo_personal " +
            "FROM cbdmq.gen_instructor_materia_paralelo imp " +
            "JOIN cbdmq.gen_instructor ins ON imp.cod_instructor = ins.cod_instructor " +
            "LEFT JOIN cbdmq.gen_dato_personal dp ON ins.cod_datos_personales = dp.cod_datos_personales " +
            "LEFT JOIN cbdmq.gen_tipo_procedencia tp ON ins.cod_tipo_procedencia = tp.cod_tipo_procedencia " +
            "LEFT JOIN cbdmq.gen_estacion_trabajo e ON ins.cod_estacion = e.cod_estacion " +
            "LEFT JOIN cbdmq.gen_unidad_gestion ug ON ins.cod_unidad_gestion = ug.cod_unidad_gestion " +
            "LEFT JOIN cbdmq.gen_tipo_contrato tc ON ins.cod_tipo_contrato = tc.cod_tipo_contrato " +
            "LEFT JOIN cbdmq.gen_tipo_instructor tins ON imp.cod_tipo_instructor = tins.cod_tipo_instructor " +
            "WHERE imp.cod_materia_paralelo = :codMateriaParalelo " +
            "AND tins.nombre_tipo_instructor = :tipoInstructor", nativeQuery = true)
    List<InstructorDatos> getInstructoresMateriaParaleloByTipo(@Param("codMateriaParalelo") Long codMateriaParalelo, @Param("tipoInstructor") String tipoInstructor);
    @Query(name = "InformacionMateriaDto.getInformacionMateria")
    List<InformacionMateriaDto> getInformacionMateria();

}
