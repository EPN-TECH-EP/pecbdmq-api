package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoRequisitoFor;
import epntech.cbdmq.pe.dominio.admin.DatosFile;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaForRepository;
import epntech.cbdmq.pe.servicio.ConvocatoriaForService;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;

@Service
public class ConvocatoriaForServiceImpl implements ConvocatoriaForService {

	@Autowired
	private ConvocatoriaForRepository repo;

	@Override
	public PeriodoAcademicoFor insertarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria, Set<Requisito> requisito, List<MultipartFile> docsPeriodoAcademico, List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion {

		return repo.insertarConvocatoriaConDocumentos(convocatoria, requisito, docsPeriodoAcademico, docsConvocatoria);

	}
}
