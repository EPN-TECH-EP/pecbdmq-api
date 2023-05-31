package epntech.cbdmq.pe.repositorio.admin;


import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodoData;

public interface MateriaPeriodoDataRepository extends JpaRepository<MateriaPeriodoData, Integer> {

	MateriaPeriodoData findByCodPeriodoAcademicoAndCodMateria(Integer periodo, Integer materia);
}

