package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;

public interface MateriaParaleloDocumentoRepository extends JpaRepository<MateriaParaleloDocumento, Integer>{

	@Transactional
	void deleteByCodMateriaParaleloAndCodDocumento(Integer materia, Integer codDocumento);
	@Query(name = "MateriaDocumentoDto.findByCodMateriaParalelo", nativeQuery = true)
	List<MateriaCursoDocumentoDto> finDocumentoRutaByCodMateriaParalelo(Integer codMateriaParalelo);
}
