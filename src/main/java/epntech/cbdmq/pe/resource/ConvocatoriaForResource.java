package epntech.cbdmq.pe.resource;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.servicio.impl.ConvocatoriaForServiceImpl;

@RestController
@RequestMapping("/convocatoriafor")
public class ConvocatoriaForResource {

	@Autowired
	private ConvocatoriaForServiceImpl objService;

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody ConvocatoriaFor convocatoria) {
		Integer modulo = convocatoria.getCodModulo();
		Integer periodo = convocatoria.getCodPeriodoAcademico();
		String estado = convocatoria.getEstado();
		Date fechaInicio = convocatoria.getFechaInicioConvocatoria();
		Date fechaFin = convocatoria.getFechaFinConvocatoria();
		LocalTime horaInicio = convocatoria.getHoraInicioConvocatoria();
		LocalTime horaFin = convocatoria.getHoraFinConvocatoria();
		String nombre = convocatoria.getNombre();
		String codigoUnico = convocatoria.getCodigoUnico();
		Integer cupoHombres = convocatoria.getCupoHombres();
		Integer cupoMujeres = convocatoria.getCupoMujeres();
		
		Set<DocumentoFor> documentos = convocatoria.getDocumentos();

		Set<DocumentoFor> docs = new HashSet<>();
		for (DocumentoFor d : documentos) {
			DocumentoFor documento = new DocumentoFor();
			documento.setAutorizacion(d.getAutorizacion());
			documento.setTipo(d.getTipo());
			documento.setDescripcion(d.getDescripcion());
			documento.setEstadoValidacion(d.getEstadoValidacion());
			documento.setCodigoUnico(d.getCodigoUnico());
			documento.setNombre(d.getNombre());
			documento.setObservaciones(d.getObservaciones());
			documento.setRuta(d.getRuta());
			documento.setEstado(d.getEstado());
			docs.add(documento);
		}

		objService.insertarConvocatoriaConDocumentos(periodo, modulo, nombre, estado, fechaInicio, fechaFin, horaInicio,
				horaFin, codigoUnico, cupoHombres, cupoMujeres, docs);

		return ResponseEntity.status(HttpStatus.CREATED).body("Convocatoria creada con éxito");
	}
}
