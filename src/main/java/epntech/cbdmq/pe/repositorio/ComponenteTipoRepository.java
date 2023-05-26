package epntech.cbdmq.pe.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.ComponenteTipo;

public interface ComponenteTipoRepository extends JpaRepository<ComponenteTipo, Integer>{
	  @Query(value="select	gcn.tipo_componente_nota as componente,\r\n"
	    		+ "		gcn.estado as estado,\r\n"
	    		+ "		gtn.tipo_nota as tiponota \r\n"
	    		+ "from cbdmq.gen_componente_nota gcn,\r\n"
	    		+ "     cbdmq.gen_tipo_nota gtn 		\r\n"
	    		+ "where gcn.cod_componente_nota=gtn.cod_componente_nota \r\n"
	    		+ "      and UPPER(gcn.estado) != 'ELIMINADO'\r\n"
	    		+ "	  and upper(gtn.estado) ='ACTIVO'", nativeQuery=true)
		List<ComponenteTipo> getComponenteTipo();
	
}
