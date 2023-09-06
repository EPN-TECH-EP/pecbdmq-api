package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pro_nota_profesionalizacion_documento")
public class ProNotaProfesionalizacionDocumento {
    @Id
    @Column(name = "cod_nota_profesionalizacion")
    private Integer CodNotaProfesionalizacion;

    @Column(name = "cod_documento")
    private Integer codDocumento;
}
