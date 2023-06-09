package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.Grado;
import epntech.cbdmq.pe.dominio.admin.Rango;
import epntech.cbdmq.pe.dominio.util.RangoDtoRead;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface RangoRepository extends JpaRepository<Rango, Integer> {
    @Query("SELECT r FROM Rango r WHERE r.codGrado.codGrado = :grado")
    List<Rango> findByGrado(@Param("grado") Integer grado);
}
