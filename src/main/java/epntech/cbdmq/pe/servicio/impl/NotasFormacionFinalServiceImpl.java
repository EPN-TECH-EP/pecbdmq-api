package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.NO_PERIODO_ACTIVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudiantesNotaDisciplina;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudiantesNotaDisciplinaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaEstudianteFormacionDto;
import epntech.cbdmq.pe.servicio.ParaleloService;
import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionFinalRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.NotasFormacionFinalService;

@Service
public class NotasFormacionFinalServiceImpl implements NotasFormacionFinalService {

	@Autowired
	private NotasFormacionFinalRepository notasFormacionFinalRepository;
	@Autowired
	private PeriodoAcademicoService periodoAcademicoSvc;
	@Autowired
	private ParaleloService paraleloSvc;
	
	@Override
	public void cargarDisciplina(List<NotasFormacionFinal> lista) throws DataException {
		Integer periodo = periodoAcademicoSvc.getPAActivo();
		if(periodo == null)
			throw new DataException(NO_PERIODO_ACTIVO);
		List<NotasFormacionFinal> nn = new ArrayList<>();
		NotasFormacionFinal notas;
		for (NotasFormacionFinal notasFormacionFinal : lista) {
			notas = notasFormacionFinalRepository.getByEstudiante(Long.valueOf(notasFormacionFinal.getCodEstudiante())).get();
			notas.setPromedioDisciplinaOficialSemana(notasFormacionFinal.getPromedioDisciplinaOficialSemana());
			if(notas == null){
				notas= new NotasFormacionFinal();
				notas=notasFormacionFinal;
				notas.setCodPeriodoAcademico(periodo);
			}
			//TODO poner que se actualice si ya existe, ademas poner que haya un constraint que sea unico

			nn.add(notas);
		}
		
		notasFormacionFinalRepository.saveAll(nn);
	}

	@Override
	public void calcularNotas() {
		notasFormacionFinalRepository.calcular_nota_final();
	}
	
	@Override
	public void cambiaEstadoRealizoEncuesta(Long codEstudiante) {
		// TODO Auto-generated method stub
		notasFormacionFinalRepository.actualizarEstadorealizoEncuesta(codEstudiante);
	}
	
	@Override
	public Boolean realizoEncuesta(Long codEstudiante) {
		// TODO Auto-generated method stub
		return notasFormacionFinalRepository.realizoEncuesta(codEstudiante);
	}

	@Override
	public Optional<NotasFormacionFinal> getByEstudiante(Long codEstudiante) {
		// TODO Auto-generated method stub
		return notasFormacionFinalRepository.getByEstudiante(codEstudiante);
	}

	@Override
	public List<EstudiantesNotaDisciplina> getEstudiantesNotaDisciplina() {
		List<EstudiantesNotaDisciplina> estudiantesNotaDisciplina = notasFormacionFinalRepository.getEstudiantesNotaDisciplina(periodoAcademicoSvc.getPAActivo());
		return estudiantesNotaDisciplina.stream().map(estudiante -> {
			Optional<NotasFormacionFinal> notasFormacionFinal = notasFormacionFinalRepository.getByEstudiante(Long.valueOf(estudiante.getCodEstudiante()));
			if(notasFormacionFinal.isPresent())
				estudiante.setPromedioDisciplinaOficialSemana(notasFormacionFinal.get().getPromedioDisciplinaOficialSemana());
			return estudiante;
		}).toList();
	}

	@Override
	public EstudiantesNotaDisciplinaDto getEstudiantesNotaDisciplinaDto() {
		EstudiantesNotaDisciplinaDto notaDisciplinaEstudianteDto = new EstudiantesNotaDisciplinaDto();
		List<Paralelo> paralelos= paraleloSvc.getParalelosPA();
		notaDisciplinaEstudianteDto.setParalelos(paralelos);
		notaDisciplinaEstudianteDto.setEstudiantesNotaDisciplina(this.getEstudiantesNotaDisciplina());
		return notaDisciplinaEstudianteDto;
	}

}

