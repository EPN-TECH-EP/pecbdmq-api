package epntech.cbdmq.pe.dominio.admin.especializacion;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "esp_delegado")
@SQLDelete(sql = "UPDATE {h-schema}esp_delegado SET estado = 'ELIMINADO' WHERE cod_esp_delegado = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

@NamedNativeQuery(name = "DelegadoEsp.findDelegados",
        query = "select ed.cod_esp_delegado as codEspDelegado, ed.cod_usuario as codUsuario, " +
                "gdp.cod_datos_personales as codDatosPersonales, ed.estado , " +
                "gdp.cedula, gdp.nombre, gdp.apellido " +
                "from cbdmq.esp_delegado ed, cbdmq.gen_usuario gu , cbdmq.gen_dato_personal gdp " +
                "where gu.cod_usuario = ed.cod_usuario " +
                "and gu.cod_datos_personales  = gdp.cod_datos_personales " +
                "and UPPER(ed.estado) = 'ACTIVO' " +
                "and UPPER(gdp.estado) = 'ACTIVO' ",
        resultSetMapping = "findDelegados")
@SqlResultSetMapping(name = "findDelegados", classes = @ConstructorResult(targetClass = DelegadoUtilEsp.class, columns = {
        @ColumnResult(name = "codEspDelegado"),
        @ColumnResult(name = "codUsuario"),
        @ColumnResult(name = "codDatosPersonales"),
        @ColumnResult(name = "estado"),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),}))
public class DelegadoEsp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_esp_delegado")
    private Long codEspDelegado;

    @Column(name = "cod_usuario")
    private Integer codUsuario;

    @Column(name = "estado")
    private String estado;
}
