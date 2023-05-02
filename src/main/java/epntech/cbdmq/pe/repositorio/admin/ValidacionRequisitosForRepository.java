package epntech.cbdmq.pe.repositorio.admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.ValidacionRequisitos;

public interface ValidacionRequisitosForRepository extends JpaRepository<ValidacionRequisitos, Integer> {

}
