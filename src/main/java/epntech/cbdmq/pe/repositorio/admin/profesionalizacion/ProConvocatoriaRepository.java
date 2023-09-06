package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProConvocatoriaRepository extends ProfesionalizacionRepository<ProConvocatoria, Integer> {
    @Override
    Optional<ProConvocatoria> findById(Integer Codigo);

    Optional<ProConvocatoria> findByEstado(String estado);
    Optional<ProConvocatoria> findByCodigoUnicoConvocatoriaAndCodigo(String CodigoUnicoConvocatoria, Integer codigo);
    Optional<ProConvocatoria> findByCodigoUnicoConvocatoria(String CodigoUnicoConvocatoria);

    @Query(value = "SELECT cbdmq.get_id_next_profesionalizacion()", nativeQuery = true)
    String findNextLastCodigo();

    Optional<ProConvocatoria> findByCodPeriodo(Integer id);

    @Query(value = "SELECT pc.estado "
            + "FROM cbdmq.pro_convocatoria pc, cbdmq.pro_periodo pp "
            + "WHERE pc.cod_periodo = pp.cod_periodo "
            + "AND UPPER(pp.estado) = 'ACTIVO'", nativeQuery = true)
    String getEstado();
}
