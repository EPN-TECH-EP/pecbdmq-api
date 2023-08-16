package epntech.cbdmq.pe.repositorio.profesionalizacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProfesionalizacionRepository<T, U> extends JpaRepository<T, U> {
}
