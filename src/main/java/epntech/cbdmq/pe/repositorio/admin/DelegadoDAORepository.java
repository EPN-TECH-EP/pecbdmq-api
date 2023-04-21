package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class DelegadoDAORepository {

	@PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void delete(Integer codUsuario, Integer codPeriodoAcademico) {
        String jpql = "DELETE FROM Delegado d WHERE d.codUsuario = :codUsuario AND d.codPeriodoAcademico = :codPeriodoAcademico";
        entityManager.createQuery(jpql)
            .setParameter("codUsuario", codUsuario)
            .setParameter("codPeriodoAcademico", codPeriodoAcademico)
            .executeUpdate();
    }
}
