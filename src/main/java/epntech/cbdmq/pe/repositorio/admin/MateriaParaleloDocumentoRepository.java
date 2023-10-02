package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;

public interface MateriaParaleloDocumentoRepository extends JpaRepository<MateriaParaleloDocumento, Integer>{

	@Transactional
	void deleteByCodMateriaParaleloAndCodDocumento(Integer materia, Integer codDocumento);
}
