/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.dominio.admin;


import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "gen_modulo_estados")
@Table(name = "gen_modulo_estados")
@Where(clause = "estado <> 'ELIMINADO'")
public class ModuloEstados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_modulo_estados")
    private Integer codigo;
    
    @Column(name = "cod_catalogo_estados")
    private Integer estadoCatalogo;
    
    @Column(name = "estado")
	private String estado;
    
    @Column(name = "orden")
    private Integer orden;
   
    @Column(name = "cod_modulo")
    private Integer modulo;
    
}
