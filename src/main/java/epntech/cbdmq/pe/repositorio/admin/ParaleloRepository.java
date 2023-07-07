package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import org.springframework.data.jpa.repository.Query;

public interface ParaleloRepository extends JpaRepository<Paralelo, Integer>{
	Optional<Paralelo> findByNombreParaleloIgnoreCase(String Nombre);

	@Query(value="select p from gen_materia_paralelo mpa \n" +
			"left join gen_materia_periodo mpe on mpa.cod_materia_periodo  = mpe.cod_materia_periodo\n" +
			"left join gen_paralelo p on mpa.cod_paralelo = p.cod_paralelo \n" +
			"where mpe.cod_periodo_academico = cbdmq.get_pa_activo()\n" +
			"group by (p)", nativeQuery = true)
	List<Paralelo> getParalelosPA();

}
