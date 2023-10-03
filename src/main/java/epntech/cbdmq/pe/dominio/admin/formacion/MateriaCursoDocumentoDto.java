package epntech.cbdmq.pe.dominio.admin.formacion;

import lombok.Data;

@Data
public class MateriaCursoDocumentoDto {
    private Integer codigoRepo;
    private Integer codDocumento;
    private Integer codigoMateriaCurso;
    private Boolean esTarea;
    private String ruta;
    private String nombre;
    private String descripcion;

    public MateriaCursoDocumentoDto(Integer codigo, Integer codDocumento, Integer codigoMateriaCurso, Boolean esTarea, String ruta, String nombre, String descripcion) {
        this.codigoRepo = codigo;
        this.codDocumento = codDocumento;
        this.codigoMateriaCurso = codigoMateriaCurso;
        this.esTarea = esTarea;
        this.ruta = ruta;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
