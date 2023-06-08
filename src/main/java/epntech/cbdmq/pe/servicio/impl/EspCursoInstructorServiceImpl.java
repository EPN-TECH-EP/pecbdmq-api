package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EspCursoInstructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EspCursoInstructorRepository;
import epntech.cbdmq.pe.servicio.EspCursoInstructorService;

@Service
public class EspCursoInstructorServiceImpl implements EspCursoInstructorService{

	@Autowired
	private EspCursoInstructorRepository espCursoInstructorRepository;
	
	@Override
	public EspCursoInstructor save(EspCursoInstructor obj) throws DataException {
		// TODO Auto-generated method stub
		return espCursoInstructorRepository.save(obj);
	}

	@Override
	public List<EspCursoInstructor> getAll() {
		// TODO Auto-generated method stub
		return espCursoInstructorRepository.findAll();
	}

	@Override
	public Optional<EspCursoInstructor> getById(int id) {
		// TODO Auto-generated method stub
		return espCursoInstructorRepository.findById(id);
	}

	@Override
	public EspCursoInstructor update(EspCursoInstructor objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return espCursoInstructorRepository.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException { 
		espCursoInstructorRepository.deleteById(id);
		
	}

}
