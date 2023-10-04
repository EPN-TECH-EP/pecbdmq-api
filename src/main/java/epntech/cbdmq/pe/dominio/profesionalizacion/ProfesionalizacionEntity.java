package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ProfesionalizacionEntity {
    @Column(name = "estado")
    private String estado;
}
