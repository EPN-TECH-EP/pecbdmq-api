package epntech.cbdmq.pe.dominio.util;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import epntech.cbdmq.pe.constante.Tablas;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity
@Table(name = Tablas.TAB_NOMBRE_TIPO_BAJA, schema =Tablas.SEC_NOMBRE_DBO)
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_baja SET estado = 'ELIMINADO' WHERE cod_tipo_baja = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoBaja {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_tipo_baja")
    private Integer codTipoBaja;
    @Column(name = "tipo_baja")
    private String baja;
    @Column(name = "estado")
	private String estado;
}
