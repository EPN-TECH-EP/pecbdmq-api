package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.RequisitoFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ConvocatoriaForServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping(path = "/convocatoriafor")
public class ConvocatoriaForResource {

	@Autowired
	private ConvocatoriaForServiceImpl objService;

	@PostMapping("/crear")
	public ResponseEntity<?> crear(@RequestParam("datosConvocatoria") String datosConvocatoria, @RequestParam("docsPeriodoAcademico") List<MultipartFile> docsPeriodoAcademico, @RequestParam("docsConvocatoria") List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {

		//Set<DocumentoFor> documentos = convocatoria.getDocumentos();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		JsonNode jsonNode = objectMapper.readTree(datosConvocatoria);
		System.out.println("jsonNode: " + jsonNode);
		
		ConvocatoriaFor convocatoria = objectMapper.readValue(datosConvocatoria, ConvocatoriaFor.class);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            //System.out.println("Clave: " + key + ", Valor: " + value.asText());
            if(key.equals("fechaInicioConvocatoria")) {
                Date fecha = dateFormat.parse(value.asText());
            	convocatoria.setFechaInicioConvocatoria(fecha);
            }
            if(key.equals("fechaFinConvocatoria")) {
                Date fecha = dateFormat.parse(value.asText());
            	convocatoria.setFechaFinConvocatoria(fecha);
            }
        }

		
		Set<RequisitoFor> requisitos = convocatoria.getRequisitos();
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

		Set<RequisitoFor> reqs = new HashSet<>();
		for (RequisitoFor r : requisitos) {
			RequisitoFor requisito = new RequisitoFor();
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
		
		if(pa == null)
			return response(HttpStatus.BAD_REQUEST, PA_ACTIVO);
		
		return new ResponseEntity<>(pa, HttpStatus.OK);
	}
	
	@PostMapping("/actualizar")
	public ResponseEntity<?> actualizar(@RequestParam("datosConvocatoria") String datosConvocatoria, @RequestParam(name = "docsConvocatoria", required = false) List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException {
	
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		JsonNode jsonNode = objectMapper.readTree(datosConvocatoria);
		System.out.println("jsonNode: " + jsonNode);
		
		ConvocatoriaFor convocatoria = objectMapper.readValue(datosConvocatoria, ConvocatoriaFor.class);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            //System.out.println("Clave: " + key + ", Valor: " + value.asText());
            if(key.equals("fechaInicioConvocatoria")) {
                Date fecha = dateFormat.parse(value.asText());
            	convocatoria.setFechaInicioConvocatoria(fecha);
            }
            if(key.equals("fechaFinConvocatoria")) {
                Date fecha = dateFormat.parse(value.asText());
            	convocatoria.setFechaFinConvocatoria(fecha);
            }
        }

		Set<DocumentoFor> documentos = convocatoria.getDocumentos();
		Set<RequisitoFor> requisitos = convocatoria.getRequisitos();

		Set<DocumentoFor> docs = new HashSet<>();
		DocumentoFor doc = new DocumentoFor();
		for (DocumentoFor d : documentos) {
			DocumentoFor documento = new DocumentoFor();
			documento.setCodigoDocumento(d.getCodigoDocumento());
			docs.add(documento);
			doc = documento;
		}

		Set<RequisitoFor> reqs = new HashSet<>();
		for (RequisitoFor r : requisitos) {
			RequisitoFor requisito = new RequisitoFor();
			requisito.setCodigoRequisito(r.getCodigoRequisito());
			reqs.add(requisito);
		}

		PeriodoAcademicoFor pa = new PeriodoAcademicoFor();
		pa = objService.actualizarConvocatoriaConDocumentos(convocatoria, reqs, docsConvocatoria, doc);
		
		
		return new ResponseEntity<>(pa, HttpStatus.OK);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
