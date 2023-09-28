package epntech.cbdmq.pe.dominio.evaluaciones;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estudiantes_anonimos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteAnonimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_estudiante_anonimo")
    private Long codEstudianteAnonimo;

    @Column(name = "token")
    private String token;

    @Column(name = "estado")
    private String estado;


}
