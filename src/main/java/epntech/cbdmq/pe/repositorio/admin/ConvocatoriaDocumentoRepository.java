package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import epntech.cbdmq.pe.dominio.admin.ConvocatoriaDocumentoForDoc;
import jakarta.transaction.Transactional;

public interface ConvocatoriaDocumentoRepository extends JpaRepository<ConvocatoriaDocumentoForDoc, Integer>{

	
	@Transactional
    @Modifying
	@Query(value="delete from cbdmq.gen_convocatoria_documento where cod_convocatoria = :convocatoria and cod_documento = :codDocumento", nativeQuery=true)
	void deleteByCodConvocatoriaAndCodDocumento(Integer convocatoria, Integer codDocumento);
	
	
	
}
