package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.constante.Tablas;
import epntech.cbdmq.pe.dominio.util.TipoBaja;

public interface Tipo_bajaRepository  extends JpaRepository<TipoBaja, Integer>{

	@Query(name=Tablas.SP_BUSCAR_TIPO_BAJA, nativeQuery = true)
    public List<TipoBaja> findByEstado(String estado);
}
