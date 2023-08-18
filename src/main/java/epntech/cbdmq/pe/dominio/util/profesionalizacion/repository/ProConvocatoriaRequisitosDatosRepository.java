package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProConvocatoriaRequisitoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProConvocatoriaRequisitosDatosRepository extends JpaRepository<ProConvocatoriaRequisitoDto, Integer> {
    @Query(value = "select pcr.cod_convocatoria_requisitos codigo, pcr.cod_requisito codigo_requisito, " +
            "pcr.cod_convocatoria codigo_convocatoria, gr.nombre_requisito, gr.descripcion_requisito, " +
            "pc.nombre nombre_convocatoria  " +
            "from cbdmq.pro_convocatoria_requisito pcr " +
            "inner join cbdmq.gen_requisito gr on pcr.cod_requisito = gr.cod_requisito " +
            "inner join cbdmq.pro_convocatoria pc on pcr.cod_convocatoria = pc.cod_convocatoria " +
            "where pcr.cod_convocatoria=:codigoConvocatoria", nativeQuery = true)
    List<ProConvocatoriaRequisitoDto> findByProConvocatoria(Integer codigoConvocatoria);

    @Query(value = "select pcr.cod_convocatoria_requisitos codigo, pcr.cod_requisito codigo_requisito,pcr.cod_convocatoria codigo_convocatoria, gr.nombre_requisito, pc.nombre nombre_convocatoria  " +
            "from cbdmq.pro_convocatoria_requisito pcr " +
            "inner join cbdmq.gen_requisito gr on pcr.cod_requisito = gr.cod_requisito " +
            "inner join cbdmq.pro_convocatoria pc on pcr.cod_convocatoria = pc.cod_convocatoria " +
            "inner join cbdmq.pro_inscripcion pi on pc.cod_convocatoria = pi.cod_convocatoria " +
            "where pi.cod_inscripcion=:codigoInscripcion", nativeQuery = true)
    List<ProConvocatoriaRequisitoDto> findByCodigoInscripcion(Integer codigoInscripcion);
}
