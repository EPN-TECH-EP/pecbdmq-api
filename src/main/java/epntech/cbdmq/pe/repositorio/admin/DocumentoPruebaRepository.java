package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.DocumentoPrueba;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DocumentoPruebaRepository extends JpaRepository<DocumentoPrueba, Integer> {
    @Transactional
    void deleteByCodPruebaDetalleAndCodDocumento(Integer codPruebaDetallle, Integer codDocumento);

    List<DocumentoPrueba> findAllByCodPruebaDetalle(Integer codPruebaDetalle);
}
