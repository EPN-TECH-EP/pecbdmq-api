package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProfesionalizacionEntity {
    @Column(name = "estado")
    private String estado;
}
