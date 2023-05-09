package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RangoDtoRead {
    private Integer cod_rango;
    private String nombre;
    private String estado;

    public RangoDtoRead() {
    }

    public RangoDtoRead(Integer cod_rango, String nombre_rango, String estado) {
        this.cod_rango = cod_rango;
        this.nombre = nombre_rango;
        this.estado = estado;
    }
}
