
package epntech.cbdmq.pe.resource.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.NotasEspecializacion;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dto.especializacion.NotasEspecializacionDTO;
import epntech.cbdmq.pe.servicio.impl.especializacion.NotasEspecializacionServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notasEspecializacion")
public class NotasEspecializacionResource {

	@Autowired
	private NotasEspecializacionServiceImpl notasEspecializacionServiceImpl;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody NotasEspecializacionDTO notas) {
		return new ResponseEntity<>(notasEspecializacionServiceImpl.save(notas), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public NotasEspecializacion getById(@PathVariable("id") int codigo) {
		return notasEspecializacionServiceImpl.getById(codigo);
	}

	@GetMapping("/listarPorCurso/{codCurso}")
	public List<NotasEspecializacionDTO> getAllByCurso(@PathVariable("codCurso") Integer codCurso) {
		return notasEspecializacionServiceImpl.getAllByCurso(codCurso);
	}

	@GetMapping("/listarAprobadosPorCurso/{codCurso}")
	public List<NotasEspecializacionDTO> getAllAprobadosByCurso(@PathVariable("codCurso") Integer codCurso) {
		return notasEspecializacionServiceImpl.getAllAprobadosByCurso(codCurso);
	}
	@GetMapping("/listarNotasPorEstudianteAndCurso")
	public List<NotaMateriaByEstudiante> getNotasByCursoAndEstudiante(@RequestParam("codCurso") Integer codCurso, @RequestParam("codEstudiante") Integer codEstudiante) {
		return notasEspecializacionServiceImpl.getHistoricosByCursoAndEstudiante(codCurso, codEstudiante);

	}

}
