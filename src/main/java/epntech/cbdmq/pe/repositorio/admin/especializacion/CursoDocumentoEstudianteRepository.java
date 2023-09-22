package epntech.cbdmq.pe.repositorio.admin.especializacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.CursoDocumentoEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoDocumentoEstudianteRepository extends JpaRepository<CursoDocumentoEstudiante, Integer>{
    @Query(value="select\n" +
            "\tecde.*\n" +
            "from\n" +
            "\tcbdmq.esp_curso_documento_estudiante ecde\n" +
            "left join cbdmq.esp_curso ec on\n" +
            "\tecde.cod_curso_especializacion = ec.cod_curso_especializacion\n" +
            "left join cbdmq.gen_estudiante ge on\n" +
            "\tecde.cod_estudiante = ge.cod_estudiante\n" +
            "where\n" +
            "\tecde.cod_curso_especializacion = :codCursoEspecializacion\n" +
            "\tand ecde.cod_estudiante = :codEstudiante",nativeQuery = true)
    List<CursoDocumentoEstudiante> getCursoDocumentoEstudianteByEstudianteCurso(Integer codEstudiante, Integer codCursoEspecializacion);


}
