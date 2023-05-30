package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_subtipo_prueba")
@SQLDelete(sql = "UPDATE {h-schema}gen_subtipo_prueba SET estado = 'ELIMINADO' WHERE cod_subtipo_prueba = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class SubTipoPrueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@EqualsAndHashCode.Include()


    @Column(name = "cod_subtipo_prueba")
    private Integer cod_subtipo_prueba;
	
	/*@Column(name = "cod_tipo_prueba")
	private Integer cod_tipo_prueba;*/


    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cod_tipo_prueba")
    private TipoPrueba tipo;
}

