package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.util.MateriaDocumento;

public interface MateriaDocumentoRepository extends JpaRepository<MateriaDocumento, Integer>{

	@Transactional
	void deleteByCodMateriaAndCodDocumento(Integer materia, Integer codDocumento);
}
