package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import epntech.cbdmq.pe.dominio.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "gen_dato_personal")
@Table(name = "gen_dato_personal")
@Data
@SQLDelete(sql = "UPDATE {h-schema}gen_dato_personal SET estado = 'ELIMINADO' WHERE cod_datos_personales = ?", check = ResultCheckStyle.COUNT)
//@Where(clause = "estado <> 'ELIMINADO'")
public class DatoPersonal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_dato_personal_cod_datos_personales_seq")
	@Column(nullable = false, updatable = false, name = "cod_datos_personales")
	private Integer cod_datos_personales;
	@Column(name = "cod_estacion")
	private Integer cod_estacion; 
	@Column(name = "cedula")
	private String cedula;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "fecha_nacimiento")
	private LocalDateTime fecha_nacimiento;
	@Column(name = "correo_personal")
	private String correo_personal;
	@Column(name = "validacion_correo")
	private String validacion_correo;
	@Column(name = "num_telef")
	private String num_telef;
	@Column(name = "ciudad")
	private String ciudad;
	@Column(name = "tipo_sangre")
	private String tipo_sangre;
	@Column(name = "cod_unidad_gestion")
	private Integer unidad;
	@Column(name = "estado")
	private String estado;
	@Column(name = "cod_provincia")
	private Integer provincia;
	
	@OneToMany(mappedBy="codDatoPersonal")
    private Set<Documento> documentos;

}
