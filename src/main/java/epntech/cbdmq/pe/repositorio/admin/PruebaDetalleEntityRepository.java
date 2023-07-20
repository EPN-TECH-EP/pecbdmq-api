package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalleEntity;

public interface PruebaDetalleEntityRepository extends JpaRepository<PruebaDetalleEntity, Integer> {

	Optional<PruebaDetalleEntity> findByCodCursoEspecializacionAndCodSubtipoPrueba(Long codCursoEspecializacion, Long subtipo);

}
