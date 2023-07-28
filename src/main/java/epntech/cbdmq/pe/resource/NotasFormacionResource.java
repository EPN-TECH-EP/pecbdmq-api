
package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.formacion.*;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import epntech.cbdmq.pe.dominio.util.NotasDatosFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.NotasFormacionFinalServiceImpl;
import epntech.cbdmq.pe.servicio.impl.NotasFormacionServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/notasFormacion")
public class NotasFormacionResource {

	@Autowired
	private NotasFormacionServiceImpl notasFormacionServiceImpl;
	@Autowired
	private NotasFormacionFinalServiceImpl notasFormacionFinalServiceImpl;


	@PostMapping("/registrar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody List<NotasFormacion> lista) {
		try {
			notasFormacionServiceImpl.saveAll(lista);
		} catch (DataException e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (MessagingException e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (PSQLException e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		return response(HttpStatus.OK, EXITO);
	}

	@GetMapping("/estudiante/{id}")
	public List<NotasFormacion> listar(@PathVariable("id") int codigo) {
		return notasFormacionServiceImpl.getByEstudiante(codigo);
	}

	@PostMapping("/disciplinaOSemana")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> registarDisciplinaOficialSemana(@RequestBody List<NotasFormacionFinal> lista)
			throws DataException {
		notasFormacionFinalServiceImpl.cargarDisciplina(lista);
		return response(HttpStatus.OK, EXITO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarDatos(@PathVariable("id") int id, @RequestBody NotasFormacion obj)
			throws DataException {
		return (ResponseEntity<NotasFormacion>) notasFormacionServiceImpl.getById(id).map(datosGuardados -> {
			datosGuardados.setNotaSupletorio(obj.getNotaSupletorio());

			NotasFormacion datosActualizados = null;
			try {
				datosActualizados = notasFormacionServiceImpl.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/notas/{idNotaEstudiante}")
	public ResponseEntity<?> actualizarNotas(@PathVariable("idNotaEstudiante") int id, @RequestBody EstudianteDatos obj)
			throws DataException {
		return (ResponseEntity<NotasFormacion>) notasFormacionServiceImpl.getById(id).map(datosGuardados -> {
			datosGuardados.setNotaSupletorio(obj.getNotaSupletorio());
			datosGuardados.setNotaMateria(obj.getNotaFinal());
			datosGuardados.setNotaDisciplina(obj.getNotaDisciplina());


			NotasFormacion datosActualizados = null;
			try {
				datosActualizados = notasFormacionServiceImpl.updateII(datosGuardados);
			} catch (DataException e) {
				// e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
		return notasFormacionServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/calcularNotas")
	public ResponseEntity<?> calcularNotas() throws DataException {
		notasFormacionFinalServiceImpl.calcularNotas();
		return response(HttpStatus.OK, PROCESO_EXITO);
	}
	@PostMapping("/listarEstudiantesByMateria/{id}")
	public NotaEstudianteFormacionDto listarNotas(@PathVariable("id") Integer codMateria) throws DataException {
        NotaEstudianteFormacionDto estudianteMateriaParalelo = notasFormacionServiceImpl.getEstudianteMateriaParalelo(codMateria);
		return estudianteMateriaParalelo;
	}

	/*actualiza el estado a true del campo realizo_prueba del estudiante*/
	@PostMapping("/actualizaEstadoRealizoEncuesta/{id}")
	public ResponseEntity<?> actualizaEstadoRealizoEncuesta(@PathVariable("id") Long id)
			throws DataException {
		notasFormacionFinalServiceImpl.cambiaEstadoRealizoEncuesta(id);
		return response(HttpStatus.OK, PROCESO_EXITO);
	}

	/*m�todo para saber si realiz� o no la encuesta, true(si realiz�), 
	 * false(no realiz�)*/
	@GetMapping("/realizoEncuesta/{id}")
	public ResponseEntity<?> realizoEncuenta(@PathVariable("id") Long codigo) {
		return response(HttpStatus.OK, Boolean.toString(notasFormacionFinalServiceImpl.realizoEncuesta(codigo)));
		
	}
	
	@GetMapping("/notasEstudiante/{id}")
	public List<NotasDatosFormacion> getNotasEstudiante(@PathVariable("id") long codigo) {
		return notasFormacionServiceImpl.getNotasEstudiante(codigo);
	}
	
	@GetMapping("/notasMateria/{id}")
	public List<NotasDatosFormacion> getNotasMateria(@PathVariable("id") long codigo) {
		return notasFormacionServiceImpl.getNotasMateria(codigo);
	}
	
    @GetMapping("/estudiantesDisciplina")
    public EstudiantesNotaDisciplinaDto getEstudiantesNotaDisciplina() {
        return notasFormacionFinalServiceImpl.getEstudiantesNotaDisciplinaDto();
    }
    @GetMapping("/listarPA")
    public List<EstudianteNotaFinalDto> getNotasDisciplina() {
        return notasFormacionFinalServiceImpl.getNotasFinalCodPeriodoAcademico();
    }
	@GetMapping("/registroEstudiantesNotas")
	public ResponseEntity<?> registroEstudiantes() {
		notasFormacionServiceImpl.insertarEstudiantesNotas();
		return response(HttpStatus.OK, PROCESO_EXITO);
	}
	@GetMapping("/listarNotaMateriaCoordinadorByEstudiante")
	public List<NotaMateriaByEstudiante> listarNotaMateriaCoordinadorByEstudiante(@Param("codEstudiante") Integer codEstudiante) {
		return notasFormacionServiceImpl.getNotaMateriasWithCoordinadorByEstudiante(codEstudiante);
	}
	@GetMapping("/listarMateriaWithCoordinador")
	public NotaMateriaByEstudiante listarNotaMateriaCoordinadorByEstudiante(@Param("codEstudiante") Integer codEstudiante, @Param("codNotaFormacion") Integer codNotaFormacion) {
		return notasFormacionServiceImpl.getMateriaWithCoordinadorByEstudianteNotaFormacion(codEstudiante, codNotaFormacion);
	}


	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
