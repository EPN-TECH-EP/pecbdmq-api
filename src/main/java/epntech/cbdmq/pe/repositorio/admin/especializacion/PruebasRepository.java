package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PruebasRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	@Transactional
	public PruebaDetalle insertPrueba(PruebaDetalle pruebaDetalle, Long codCursoEspecializacion, List<MultipartFile> documentos) {
		
		return pruebaDetalle;
		
	}
}
