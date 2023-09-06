package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProDelegadoDatosDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProDelegadoDatosRepository extends JpaRepository<ProDelegadoDatosDto, Integer> {

    @Query(value = "select dl.cod_delegado, dp.cedula, dp.nombre, dp.apellido, dp.correo_personal, dp.cod_datos_personales " +
            "from cbdmq.pro_delegados dl inner join cbdmq.gen_dato_personal dp on dl.cod_usuario= dp.cod_datos_personales where dl.estado='ACTIVO'" , nativeQuery = true)
    List<ProDelegadoDatosDto> getAllAssigned();

}
