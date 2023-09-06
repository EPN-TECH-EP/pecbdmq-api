package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProCumpleRequisitos;

import java.util.Optional;

public interface ProCumpleRequisitosRepository extends ProfesionalizacionRepository<ProCumpleRequisitos, Integer> {


        Optional<ProCumpleRequisitos> findByCodInscripcionAndCodRequisito(int codInscripcion, int codRequisito);


}
