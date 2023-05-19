package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.PADocumento;

public interface PADocumentoRepository extends JpaRepository<PADocumento, Integer> {

	PADocumento findByCodDocumentoAndCodPeriodoAcademico(Integer cod_documento, Integer cod_pa);
}
