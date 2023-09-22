package epntech.cbdmq.pe.dominio.fichaPersonal.especializacion;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="esp_curso_documento_estudiante")
public class CursoDocumentoEstudiante{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_documento_estudiante_curso")
    private Integer codEstudianteMateriaDocumento;
    @Column(name = "cod_documento")
    private Integer codDocumento;
    @Column(name = "cod_estudiante")
    private Integer codEstudiante;
    @Column(name = "cod_curso_especializacion")
    private Integer codCursoEspecializacion;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private String estado;

}