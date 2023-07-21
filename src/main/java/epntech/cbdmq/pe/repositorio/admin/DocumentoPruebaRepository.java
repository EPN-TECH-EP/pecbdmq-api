package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.DocumentoPrueba;

public interface DocumentoPruebaRepository extends JpaRepository<DocumentoPrueba, Integer> {
    @Transactional
    void deleteByCodPruebaDetalleAndCodDocumento(Integer codPruebaDetallle, Integer codDocumento);
    
    List<DocumentoPrueba> findAllByCodPruebaDetalle(Integer codPruebaDetalle);

}
