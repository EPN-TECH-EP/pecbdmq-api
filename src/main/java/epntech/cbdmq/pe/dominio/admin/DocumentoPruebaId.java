package epntech.cbdmq.pe.dominio.admin;

import lombok.Data;

import java.io.Serializable;
@Data
public class DocumentoPruebaId implements Serializable {
    private static final long serialVersionUID = -7100801367579540083L;

    private Integer codPruebaDetalle;
    private Integer codDocumento;
}
