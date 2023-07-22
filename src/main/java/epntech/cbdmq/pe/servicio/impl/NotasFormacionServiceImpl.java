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
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.servicio.formacion.EstudianteMateriaParaleloService;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
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
import jakarta.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotasFormacionServiceImpl implements NotasFormacionService {

	@Autowired
	private NotasFormacionRepository notasFormacionRepository;
	@Autowired
	private MateriaPeriodoDataRepository materiaPeriodoDataRepository;
	@Autowired
	private PeriodoAcademicoService periodoAcademicoService;
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
	@Autowired
	private EstudianteMateriaParaleloService estudianteMateriaParaleloService;
	@Autowired
	private MateriaParaleloService materiaParaleloService;
	@Autowired
	private MateriaPeriodoService materiaPeriodoService;

	@Override
	@Async
	public void saveAll(List<NotasFormacion> lista) throws DataException, MessagingException, PSQLException {
		NotasFormacion nn = new NotasFormacion();
		List<NotasFormacion> listaNotasFormacion = new ArrayList<>();
		Integer periodo = periodoAcademicoService.getPAActivo();

		if (periodo == null)
			throw new DataException(NO_PERIODO_ACTIVO);

		Integer i = 0;
		for (NotasFormacion notasFormacion : lista) {
			EstudianteMateriaParalelo objMP=estudianteMateriaParaleloService.findByNotaFormacion(notasFormacion.getCodNotaFormacion()).get();
			MateriaParalelo objMpa= materiaParaleloService.findByEstudianteMateriaParalelo(objMP.getCodEstudianteMateriaParalelo()).get();
			MateriaPeriodo objMpe= materiaPeriodoService.findByMateriaParalelo(objMpa.getCodMateriaParalelo()).get();

			if (notasFormacionRepository.existeNota(objMpe.getCodMateria(),
					objMP.getCodEstudiante()) == 0) {

				LocalDateTime fecha = LocalDateTime.now();

				nn = lista.get(i);
				MateriaPeriodoData materiaPeriodoData = new MateriaPeriodoData();
				materiaPeriodoData = materiaPeriodoDataRepository.findByCodPeriodoAcademicoAndCodMateria(periodo,
						objMpe.getCodMateria());

				nn.setEstado("ACTIVO");
				nn.setNotaMinima(materiaPeriodoData.getNotaMinima());
				nn.setNumeroHoras(materiaPeriodoData.getNumeroHoras());
				nn.setPesoMateria(materiaPeriodoData.getPesoMateria());
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
		EstudianteMateriaParalelo estudianteMateriaParalelo= estudianteMateriaParaleloService.findByNotaFormacion(objActualizado.getCodNotaFormacion()).get();
		MateriaParalelo materiaPa = materiaParaleloService.findByEstudianteMateriaParalelo(estudianteMateriaParalelo.getCodEstudianteMateriaParalelo()).get();
		MateriaPeriodo materiaPe= materiaPeriodoService.findByMateriaParalelo(materiaPa.getCodMateriaParalelo()).get();
		materiaPeriodoData = materiaPeriodoDataRepository.findByCodPeriodoAcademicoAndCodMateria(
				materiaPe.getCodPeriodoAcademico(), materiaPe.getCodMateria());

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
		MateriaPeriodoData materiaPeriodoData = new MateriaPeriodoData();
		EstudianteMateriaParalelo estudianteMateriaParalelo= estudianteMateriaParaleloService.findByNotaFormacion(objActualizado.getCodNotaFormacion()).get();
		MateriaParalelo materiaPa = materiaParaleloService.findByEstudianteMateriaParalelo(estudianteMateriaParalelo.getCodEstudianteMateriaParalelo()).get();
		MateriaPeriodo materiaPe= materiaPeriodoService.findByMateriaParalelo(materiaPa.getCodMateriaParalelo()).get();
		materiaPeriodoData = materiaPeriodoDataRepository.findByCodPeriodoAcademicoAndCodMateria(periodoAcademicoService.getPAActivo(),
				materiaPe.getCodMateria());
		objActualizado.setNotaPonderacion(materiaPeriodoData.getPesoMateria() * objActualizado.getNotaMateria());
		return notasFormacionRepository.save(objActualizado);
	}

	@Override
	public Optional<NotasFormacion> getById(int id) {
		// TODO Auto-generated method stub
		return notasFormacionRepository.findById(id);
	}

	private void enviarCorreo(List<NotasFormacion> lista) throws MessagingException {

		for (NotasFormacion notasFormacion : lista) {
			EstudianteMateriaParalelo estudianteMateriaParalelo= estudianteMateriaParaleloService.findByNotaFormacion(notasFormacion.getCodNotaFormacion()).get();
			MateriaParalelo materiaPa = materiaParaleloService.findByEstudianteMateriaParalelo(estudianteMateriaParalelo.getCodEstudianteMateriaParalelo()).get();
			MateriaPeriodo materiaPe= materiaPeriodoService.findByMateriaParalelo(materiaPa.getCodMateriaParalelo()).get();
			Optional<Estudiante> estudiante = estudianteRepository.findById(estudianteMateriaParalelo.getCodEstudiante());
			Optional<DatoPersonal> datoPersonal = datoPersonalRepository
					.findById(estudiante.get().getCodDatosPersonales());
			Optional<Materia> materia = materiaRepository.findById(materiaPe.getCodMateria());

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
		notaEstudianteFormacionDto.setEstudianteDatos(notasFormacionRepository.getEstudianteMateriaParalelo(codMateria, periodoAcademicoService.getPAActivo()));
		return notaEstudianteFormacionDto;
	}

	@Override
	@Transactional
	public void insertarEstudiantesNotas() {
		try {
			notasFormacionRepository.insertar_lista_estudiantes_notas();
		} catch (Exception ex) {
			ex.printStackTrace(); // Imprimir la traza de la excepción en la consola
			throw new RuntimeException("Error al intentar ejecutar el procedimiento almacenado");
		}

	}

	@Override
	public List<NotaMateriaByEstudiante> getNotaMateriasByEstudiante(Integer codEstudiante, String tipoInstructor) {
		return notasFormacionRepository.get(codEstudiante, tipoInstructor, periodoAcademicoService.getPAActivo());
	}

	@Override
	public List<NotaMateriaByEstudiante> getNotaMateriasCoordinadorByEstudiante(Integer codEstudiante) {
		return this.getNotaMateriasByEstudiante(codEstudiante,"COORDINADOR");
	}
}
