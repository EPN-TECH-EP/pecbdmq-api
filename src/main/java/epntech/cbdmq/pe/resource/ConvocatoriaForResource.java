package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.DatosFile;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.servicio.impl.ConvocatoriaForServiceImpl;

@RestController
@RequestMapping("/convocatoriafor")
public class ConvocatoriaForResource {

	@Autowired
	private ConvocatoriaForServiceImpl objService;

	@PostMapping("/crear")
	public ResponseEntity<?> crear(@RequestBody ConvocatoriaFor convocatoria, @RequestParam List<MultipartFile> docsPeriodoAcademico, @RequestParam List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion {

		//Set<DocumentoFor> documentos = convocatoria.getDocumentos();
		Set<Requisito> requisitos = convocatoria.getRequisitos();
		//Set<DocumentoRequisitoFor> documentosRequisito = null;

		/*Set<DocumentoFor> docs = new HashSet<>();
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
		}*/

		Set<Requisito> reqs = new HashSet<>();
		for (Requisito r : requisitos) {
			Requisito requisito = new Requisito();
			requisito.setCodigoRequisito(r.getCodigoRequisito());
			//requisito.setCodFuncionario(r.getCodFuncionario());
			//requisito.setDescripcion(r.getDescripcion());
			//requisito.setNombre(r.getNombre());
			//requisito.setEstado(r.getEstado());
			reqs.add(requisito);
		}

		/*
		// documentos de requisitos

		List<DocumentoRequisitoFor> documentosDTO = new ArrayList<>();

		Iterator<Requisito> req = requisitos.iterator();
		while (req.hasNext()) {
			Requisito elemento = req.next();
			System.out.println("elemento: " + elemento.getDocumentosRequisito().toArray()[0]);

			for (DocumentoRequisitoFor documento : elemento.getDocumentosRequisito()) {
				DocumentoRequisitoFor documentoDTO = new DocumentoRequisitoFor();
				documentoDTO.setNombre(documento.getNombre());
				documentoDTO.setTipo(documento.getTipo());
				documentoDTO.setAutorizacion(documento.getAutorizacion());
				documentoDTO.setCodigoUnico(documento.getCodigoUnico());
				documentoDTO.setDescripcion(documento.getDescripcion());
				documentoDTO.setEstado(documento.getEstado());
				documentoDTO.setEstadoValidacion(documento.getEstadoValidacion());

				documentosDTO.add(documentoDTO);
			}
		}

		Set<DocumentoRequisitoFor> docsRequisito = new HashSet<>(documentosDTO);*/
		
		/*for (DocumentoRequisitoFor d : documentosRequisito) {
			DocumentoRequisitoFor documento = new DocumentoRequisitoFor();
			documento.setAutorizacion(d.getAutorizacion());
			documento.setTipo(d.getTipo());
			documento.setDescripcion(d.getDescripcion());
			documento.setEstadoValidacion(d.getEstadoValidacion());
			documento.setCodigoUnico(d.getCodigoUnico());
			documento.setNombre(d.getNombre());
			documento.setObservaciones(d.getObservaciones());
			documento.setRuta(d.getRuta());
			documento.setEstado(d.getEstado());
			docsRequisito.add(documento);
		}*/

		PeriodoAcademicoFor pa = new PeriodoAcademicoFor();
		pa = objService.insertarConvocatoriaConDocumentos(convocatoria, reqs, docsPeriodoAcademico, docsConvocatoria);
		
		
		return new ResponseEntity<>(pa, HttpStatus.OK);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
