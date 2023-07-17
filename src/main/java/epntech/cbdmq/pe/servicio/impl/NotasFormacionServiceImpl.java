package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.FormacionConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.EmailConst.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteDatos;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaEstudianteFormacionDto;
import epntech.cbdmq.pe.servicio.ParaleloService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.util.NotasDatosFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoDataRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.repositorio.admin.NotasDatosFormacionRepository;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.NotasFormacionService;
import jakarta.mail.MessagingException;

@Service
public class NotasFormacionServiceImpl implements NotasFormacionService {

	@Autowired
	private NotasFormacionRepository notasFormacionRepository;
	@Autowired
	private MateriaPeriodoDataRepository materiaPeriodoDataRepository;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private DatoPersonalRepository datoPersonalRepository;
	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private MateriaRepository materiaRepository;
	@Autowired
	private NotasDatosFormacionRepository notasDatosFormacionRepository;
	@Autowired
	private ParaleloService paraleloSvc;

	@Override
	@Async
	public void saveAll(List<NotasFormacion> lista) throws DataException, MessagingException, PSQLException {
		NotasFormacion nn = new NotasFormacion();
		List<NotasFormacion> listaNotasFormacion = new ArrayList<>();
		Integer periodo = periodoAcademicoRepository.getPAActive();

		if (periodo == null)
			throw new DataException(NO_PERIODO_ACTIVO);

		Integer i = 0;
		for (NotasFormacion notasFormacion : lista) {

			if (notasFormacionRepository.existeNota(notasFormacion.getCodInstructor(), notasFormacion.getCodMateria(),
					notasFormacion.getCodEstudiante()) == 0) {

				LocalDateTime fecha = LocalDateTime.now();

				nn = lista.get(i);
				MateriaPeriodoData materiaPeriodoData = new MateriaPeriodoData();
				materiaPeriodoData = materiaPeriodoDataRepository.findByCodPeriodoAcademicoAndCodMateria(periodo,
						notasFormacion.getCodMateria());

				nn.setEstado("ACTIVO");
				nn.setNotaMinima(materiaPeriodoData.getNotaMinima());
				nn.setNumeroHoras(materiaPeriodoData.getNumeroHoras());
				nn.setPesoMateria(materiaPeriodoData.getPesoMateria());
				nn.setCodPeriodoAcademico(periodo);
				nn.setNotaPonderacion(materiaPeriodoData.getPesoMateria() * nn.getNotaMateria());
				nn.setFechaIngreso(fecha);

				listaNotasFormacion.add(nn);
			}
			
			i++;
		}

		notasFormacionRepository.saveAll(listaNotasFormacion);

		enviarCorreo(listaNotasFormacion);

	}

	@Override
	public List<NotasFormacion> getByEstudiante(int id) {
		// TODO Auto-generated method stub
		return notasFormacionRepository.findByCodEstudiante(id);
	}

	@Override
	public NotasFormacion update(NotasFormacion objActualizado) throws DataException {
		MateriaPeriodoData materiaPeriodoData = new MateriaPeriodoData();
		materiaPeriodoData = materiaPeriodoDataRepository.findByCodPeriodoAcademicoAndCodMateria(
				objActualizado.getCodPeriodoAcademico(), objActualizado.getCodMateria());

		if (objActualizado.getNotaSupletorio() < materiaPeriodoData.getNotaMinima()) {
			throw new DataException(NOTA_MINIMA_MATERIA);
		} else if (objActualizado.getNotaMateria() >= materiaPeriodoData.getNotaMinimaSupletorioInicio()
				&& objActualizado.getNotaMateria() <= materiaPeriodoData.getNotaMinimaSupletorioFin()) {
			objActualizado.setNotaPonderacion(materiaPeriodoData.getPesoMateria() * materiaPeriodoData.getNotaMinima());
			return notasFormacionRepository.save(objActualizado);
		} else
			throw new DataException(NOTA_MATERIA);
	}

	@Override
	public NotasFormacion updateII(NotasFormacion objActualizado) throws DataException {
		Optional<NotasFormacion> notasFormacion = this.getById(objActualizado.getCodNotaFormacion());
		if(notasFormacion.isEmpty()){
			throw new DataException(NO_ENCUENTRA);
		}

		return notasFormacionRepository.save(objActualizado);
	}

	@Override
	public Optional<NotasFormacion> getById(int id) {
		// TODO Auto-generated method stub
		return notasFormacionRepository.findById(id);
	}

	private void enviarCorreo(List<NotasFormacion> lista) throws MessagingException {

		for (NotasFormacion notasFormacion : lista) {
			Optional<Estudiante> estudiante = estudianteRepository.findById(notasFormacion.getCodEstudiante());
			Optional<DatoPersonal> datoPersonal = datoPersonalRepository
					.findById(estudiante.get().getCodDatosPersonales());
			Optional<Materia> materia = materiaRepository.findById(notasFormacion.getCodMateria());

			emailService.enviarEmail(datoPersonal.get().getCorreoPersonal(), EMAIL_SUBJECT_REG_NOTA,
					"Se ha registrado una nota en la materia: " + materia.get().getNombre());
		}
	}

	@Override
	public List<NotasDatosFormacion> getNotasEstudiante(long codEstudiante) {
		// TODO Auto-generated method stub
		return notasDatosFormacionRepository.getNotasEstudiante(codEstudiante);
	}

	@Override
	public List<NotasDatosFormacion> getNotasMateria(long codMateria) {
		// TODO Auto-generated method stub
		return notasDatosFormacionRepository.getNotasMateria(codMateria);
	}

	@Override
	public NotaEstudianteFormacionDto getEstudianteMateriaParalelo(Integer codMateria) {
		NotaEstudianteFormacionDto notaEstudianteFormacionDto = new NotaEstudianteFormacionDto();
		List<Paralelo> paralelos= paraleloSvc.getParalelosPA();
		notaEstudianteFormacionDto.setParalelos(paralelos);
		notaEstudianteFormacionDto.setEstudianteDatos(notasFormacionRepository.getEstudianteMateriaParalelo(codMateria, periodoAcademicoRepository.getPAActive()));
		return notaEstudianteFormacionDto;
	}
}
