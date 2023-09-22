package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EstudianteMateriaDocumentoRepository extends JpaRepository<EstudianteMateriaDocumento, Integer> {
    
    @Query(value="select gedm.* from cbdmq.gen_estudiante_materia_documento gedm\n" +
            "\tleft join cbdmq.gen_estudiante_materia_paralelo gemp on gedm.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo \n" +
            "\twhere gemp.cod_materia_paralelo = :codMateriaParalelo\n" +
            "\tand gemp.cod_estudiante = :codEstudiante",nativeQuery = true)
    List <EstudianteMateriaDocumento> getEstudianteMateriaDocumentoByEstudianteMateriaParalelo(Integer codEstudiante, Integer codMateriaParalelo);


}
