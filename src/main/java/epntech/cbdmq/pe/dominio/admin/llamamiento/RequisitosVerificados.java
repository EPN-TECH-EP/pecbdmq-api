package epntech.cbdmq.pe.dominio.admin.llamamiento;

import lombok.Data;

@Data
public class RequisitosVerificados {
    private Integer codRequisito;
    private String nombreRequisito;
    private String descripcionRequisito;
    private Boolean esAprobado;
    private String periodo;

    public RequisitosVerificados(Integer codRequisito, String nombreRequisito, String descripcionRequisito, Boolean esAprobado, String periodo) {
        this.codRequisito = codRequisito;
        this.nombreRequisito = nombreRequisito;
        this.descripcionRequisito = descripcionRequisito;
        this.esAprobado = esAprobado;
        this.periodo= periodo;
    }
}
