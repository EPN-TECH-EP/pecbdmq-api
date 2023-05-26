package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;

public interface SubTipoPruebaRepository extends JpaRepository<SubTipoPrueba, Integer> {
	//Optional<SubTipoPrueba> findByCodTipoPrueba(Integer id);

}
