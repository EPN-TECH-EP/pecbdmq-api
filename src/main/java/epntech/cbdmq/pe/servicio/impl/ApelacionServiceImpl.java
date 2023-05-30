package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.FormacionConst.FECHA_APELACION_INVALIDA;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ApelacionRepository;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionFinalRepository;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionRepository;
import epntech.cbdmq.pe.servicio.ApelacionService;

@Service
public class ApelacionServiceImpl implements ApelacionService {

	@Autowired
	private ApelacionRepository repo;
	@Autowired
	private NotasFormacionRepository notasFormacionRepository;
	@Autowired
	private NotasFormacionFinalRepository notasFormacionFinalRepository;

	@Override
	public Apelacion save(Apelacion obj) throws DataException, ParseException {
		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = sdf.format(fechaActual);
		Date date = sdf.parse(fechaFormateada);

		Optional<NotasFormacion> notasFormacion = notasFormacionRepository.findById(obj.getCodNotaFormacion());

		if (notasFormacion.isPresent()) {
			LocalDateTime dateTime = LocalDateTime.now();
			LocalDateTime fechaIngreso = notasFormacion.get().getFechaIngreso();
			LocalDateTime newDateTime = notasFormacion.get().getFechaIngreso().plusHours(24);

			if (!(dateTime.isAfter(fechaIngreso) && dateTime.isBefore(newDateTime)))
				throw new DataException(FECHA_APELACION_INVALIDA);

			obj.setEstado("ACTIVO");
			obj.setFechaSolicitud(date);
			obj.setNotaActual(notasFormacion.get().getNotaMateria());

		} else {
			throw new DataException(REGISTRO_NO_EXISTE);
		}

		return repo.save(obj);

	}

	@Override
	public List<Apelacion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Apelacion> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Apelacion update(Apelacion objActualizado) throws DataException {

		if (objActualizado.getAprobacion()) {
			Optional<NotasFormacion> nn = notasFormacionRepository.findById(objActualizado.getCodNotaFormacion());
			if (nn.isPresent()) {
				NotasFormacion notasFormacion = new NotasFormacion();
				notasFormacion = nn.get();
				notasFormacion.setNotaMateria(objActualizado.getNotaNueva());
				System.out.println("notasFormacion.getPesoMateria(): " + notasFormacion.getPesoMateria());
				notasFormacion.setNotaPonderacion(objActualizado.getNotaNueva() * notasFormacion.getPesoMateria());

				notasFormacionRepository.save(notasFormacion);

				notasFormacionFinalRepository.calcular_notafinal_x_estudiante(notasFormacion.getCodEstudiante());
			}
		}
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		repo.deleteById(codigo);
	}

	@Override
	public List<Apelacion> getByEstudiante(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.getApelacionesByEstudiante(codigo);
	}

	@Override
	public List<Apelacion> getByInstructor(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.getApelacionesByInstructor(codigo);
	}

	@Override
	public Boolean validaFechaApelacion(Integer codNotaFormacion) throws DataException {
		Boolean r = false;
		Optional<NotasFormacion> notasFormacion = notasFormacionRepository.findById(codNotaFormacion);

		if (notasFormacion.isPresent()) {
			LocalDateTime dateTime = LocalDateTime.now();
			LocalDateTime fechaIngreso = notasFormacion.get().getFechaIngreso();
			LocalDateTime newDateTime = notasFormacion.get().getFechaIngreso().plusHours(24);

			if (!(dateTime.isAfter(fechaIngreso) && dateTime.isBefore(newDateTime)))
				r = false;
			else
				r = true;
		}else
			throw new DataException(REGISTRO_NO_EXISTE);
		
		return r;
	}

}
