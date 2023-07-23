package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface InstructorMateriaParaleloRepository extends JpaRepository<InstructorMateriaParalelo,Integer> {


    //no vale
    @Query(nativeQuery = true, name = "InformacionMateriaDto.find")
    List<InformacionMateriaDto> getInformacionMateria();
    @Transactional
    void deleteByCodInstructorAndCodTipoInstructor(Integer codInstructor, Integer codTipoInstructor);
    @Transactional
    void deleteByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(Integer codInstructor, Integer codTipoInstructor,Integer codMateriaParalelo);
    @Transactional
    void deleteAllByCodTipoInstructor(Integer codTipoInstructor);

}
