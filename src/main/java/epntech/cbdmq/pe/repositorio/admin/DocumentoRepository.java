package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

}
