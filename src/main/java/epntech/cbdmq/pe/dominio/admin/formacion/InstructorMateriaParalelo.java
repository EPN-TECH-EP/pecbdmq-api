package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="gen_instructor_materia_paralelo")
@NamedNativeQuery(name = "InformacionMateriaDto.getInformacionMateria",
        query = "select gmp.cod_materia_paralelo , gm.nombre_materia,em.nombre_eje_materia,p.nombre_paralelo,a.cod_aula from cbdmq.gen_instructor_materia_paralelo gimp\n" +
                "left join cbdmq.gen_materia_paralelo gmp on gmp.cod_materia_paralelo = gimp.cod_materia_paralelo \n" +
                "left join cbdmq.gen_materia_periodo gmpe on gmpe.cod_materia_periodo = gmp.cod_materia_periodo \n" +
                "left join cbdmq.gen_materia gm on gm.cod_materia = gmpe.cod_materia\n" +
                "left join cbdmq.gen_eje_materia em on em.cod_eje_materia = gm.cod_eje_materia \n" +
                "left join cbdmq.gen_paralelo p on gmp.cod_paralelo = p.cod_paralelo \n" +
                "left join cbdmq.gen_aula a on gmpe.cod_aula = a.cod_aula \n" +
                "group by (gmp.cod_materia_paralelo,gm.nombre_materia,em.nombre_eje_materia,p.nombre_paralelo, a.cod_aula)\n",
        resultSetMapping = "InformacionMateriaDto"
)
@SqlResultSetMapping(name = "InformacionMateriaDto", classes = @ConstructorResult(targetClass = InformacionMateriaDto.class, columns = {
        @ColumnResult(name = "cod_materia_paralelo"),
        @ColumnResult(name = "nombre_materia"),
        @ColumnResult(name = "nombre_eje_materia"),
        @ColumnResult(name = "nombre_paralelo"),
        @ColumnResult(name = "cod_aula"),
}))
public class InstructorMateriaParalelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_instructor_materia_paralelo")
    private Integer codInstructorMateriaParalelo;
    @Column(name = "cod_instructor")
    private Integer codInstructor;
    @Column(name = "cod_tipo_instructor")
    private Integer codTipoInstructor;
    @Column(name = "cod_materia_paralelo")
    private Integer codMateriaParalelo;
    @Column(name = "estado")
    private String estado;

}
