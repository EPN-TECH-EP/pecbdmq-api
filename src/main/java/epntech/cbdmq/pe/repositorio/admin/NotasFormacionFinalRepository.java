package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;

public interface NotasFormacionFinalRepository extends JpaRepository<NotasFormacionFinal, Integer> {

	@Procedure(value = "cbdmq.calcular_nota_final")
	Integer calcular_nota_final();
}

