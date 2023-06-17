package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.MateriaEstudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.MateriaEstudianteRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.MateriaEstudianteService;

@Service
public class MateriaEstudianteServiceImpl implements MateriaEstudianteService {
	
	@Autowired
	private MateriaEstudianteRepository materiaEstudianteRepository;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository; 

	@Override
	public MateriaEstudiante save(MateriaEstudiante materiaEstudiante) throws DataException {
		Optional<MateriaEstudiante> materiaEstudianteOptional = materiaEstudianteRepository.getMateriaEstudiante(materiaEstudiante.getCodMateria(), materiaEstudiante.getCodEstudiante());
		if(materiaEstudianteOptional.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);
		
		materiaEstudiante.setCodPeriodoAcademico((long) periodoAcademicoRepository.getPAActive());
		return materiaEstudianteRepository.save(materiaEstudiante);
	}

	@Override
	public void delete(Long codMateriaEstudiante) {
		materiaEstudianteRepository.deleteById(codMateriaEstudiante);
		
	}

	@Override
	public List<MateriaEstudiante> getByCodEstudiante(Long codEstudiante) {
		// TODO Auto-generated method stub
		return materiaEstudianteRepository.findByCodEstudiante(codEstudiante);
	}

}
