package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Apelacion;

public interface ApelacionRepository extends JpaRepository<Apelacion, Integer>{
	@Query(value = "select a.* "
			+ "from cbdmq.gen_apelacion a, cbdmq.gen_nota_formacion n "
			+ "where a.cod_nota_formacion = n.cod_nota_formacion "
			+ "and n.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and upper(a.estado) = 'ACTIVO' "
			+ "and upper(n.estado) = 'ACTIVO' "
			+ "and n.cod_estudiante = :codEstudiante", nativeQuery=true)
	List<Apelacion> getApelacionesByEstudiante(Integer codEstudiante);
	
	@Query(value = "select a.* "
			+ "from cbdmq.gen_apelacion a, cbdmq.gen_nota_formacion n "
			+ "where a.cod_nota_formacion = n.cod_nota_formacion "
			+ "and n.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and upper(a.estado) = 'ACTIVO' "
			+ "and upper(n.estado) = 'ACTIVO' "
			+ "and n.cod_instructor = :codInstructor", nativeQuery=true)
	List<Apelacion> getApelacionesByInstructor(Integer codInstructor);
	
}
