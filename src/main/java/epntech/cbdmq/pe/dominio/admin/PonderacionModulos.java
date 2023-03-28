package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class PonderacionModulos {

	@Id
	private Integer codigo;
	
	private String modulo;
	
	private String Periodo;
	
	private String componente;
	
	private String tiponota;
	
	private Integer porcentajefinal;
	
	private Integer porcentajenota;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicioVigencia;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFinVigencia;
	
	
	
	
	
}
