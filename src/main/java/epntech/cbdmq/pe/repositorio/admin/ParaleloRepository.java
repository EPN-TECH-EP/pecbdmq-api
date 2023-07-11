package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParaleloRepository extends JpaRepository<Paralelo, Integer>{
	Optional<Paralelo> findByNombreParaleloIgnoreCase(String Nombre);

	@Query("select p from MateriaParalelo mpa \n" +
			"left join MateriaPeriodo mpe on mpa.codMateriaPeriodo = mpe.codMateriaPeriodo\n" +
			"left join gen_paralelo p on mpa.codParalelo = p.codParalelo\n" +
			"where mpe.codPeriodoAcademico = :codPA\n" +
			"group by (p)")
	List<Paralelo> getParalelosPA(@Param("codPA") Integer codPA);

}
