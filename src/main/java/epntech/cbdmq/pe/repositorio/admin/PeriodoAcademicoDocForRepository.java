package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoDocumentoFor;
import org.springframework.transaction.annotation.Transactional;

public interface PeriodoAcademicoDocForRepository extends JpaRepository<PeriodoAcademicoDocumentoFor, Integer> {
    @Transactional
    void deleteByCodPeriodoAcademicoAndCodDocumento(Integer codPeriodoAcademico, Integer codDocumento);

}
