package epntech.cbdmq.pe.servicio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodoData;
import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoDataRepository;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.NotasFormacionService;

@Service
public class NotasFormacionServiceImpl implements NotasFormacionService {
	
	@Autowired
	private NotasFormacionRepository notasFormacionRepository;
	@Autowired
	private MateriaPeriodoDataRepository materiaPeriodoDataRepository;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;

	@Override
	public void saveAll(List<NotasFormacion> lista) {
		NotasFormacion nn = new NotasFormacion();
		List<NotasFormacion> listaNotasFormacion = new ArrayList<>();
		int periodo = periodoAcademicoRepository.getPAActive();
		Integer i = 0; 
		for (NotasFormacion notasFormacion : lista) {
			nn = lista.get(i);
			MateriaPeriodoData materiaPeriodoData = new MateriaPeriodoData();
			materiaPeriodoData = materiaPeriodoDataRepository.findByCodPeriodoAcademicoAndCodMateria(periodo, notasFormacion.getCodMateria());
			
			nn.setEstado("ACTIVO");
			nn.setNotaMinima(materiaPeriodoData.getNotaMinima());
			nn.setNumeroHoras(materiaPeriodoData.getNumeroHoras());
			nn.setPesoMateria(materiaPeriodoData.getPesoMateria());
			nn.setCodPeriodoAcademico(periodo);
			nn.setNotaPonderacion(materiaPeriodoData.getPesoMateria() * nn.getNotaMateria());
			
			listaNotasFormacion.add(nn);
			
			i++;
		}
		
		notasFormacionRepository.saveAll(listaNotasFormacion);
	}

	@Override
	public List<NotasFormacion> getByEstudiante(int id) {
		// TODO Auto-generated method stub
		return notasFormacionRepository.findByCodEstudiante(id);
	}

	@Override
	public NotasFormacion update(NotasFormacion objActualizado) {
		// TODO Auto-generated method stub
		return notasFormacionRepository.save(objActualizado);
	}

	@Override
	public Optional<NotasFormacion> getById(int id) {
		// TODO Auto-generated method stub
		return notasFormacionRepository.findById(id);
	}

}
