package epntech.cbdmq.pe.dominio.admin.llamamiento;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_funcionario_documento")
public class FuncionarioDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_documento_funcionario")
    private Integer codDocumentoFuncionario;
    @Column(name = "cod_funcionario")
    private Integer codFuncionario;
    @Column(name = "cod_documento")
    private Integer codDocumento;
    @Column(name = "es_reconocimiento")
    private Boolean esReconocimiento=false;
    @Column(name = "es_sancion")
    private Boolean esSancion= false;
    @Column(name = "observacion")
    private String observacion;
}
