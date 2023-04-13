package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gen_rol")
@Data

public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_rol_cod_rol_seq")
	@SequenceGenerator(name = "gen_rol_cod_rol_seq", sequenceName = "gen_rol_cod_rol_seq", allocationSize = 1)
	@Column(nullable = false, updatable = false)
	protected Long codRol;
	protected String nombre;
	protected String descripcion;

	public Rol() {

	}

	public Rol(Long cod_rol, String nombre, String descripcion) {
		this.codRol = cod_rol;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

}
