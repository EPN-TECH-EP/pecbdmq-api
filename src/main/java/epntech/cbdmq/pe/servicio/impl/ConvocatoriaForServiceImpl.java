package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.PA_ACTIVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.RequisitoFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaForRepository;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaForUpdRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.ConvocatoriaForService;
import jakarta.mail.MessagingException;

@Service
public class ConvocatoriaForServiceImpl implements ConvocatoriaForService {

	@Autowired
	private ConvocatoriaForRepository repo;
	
	@Autowired
	private ConvocatoriaForUpdRepository repo1;
	
	@Autowired
	private PeriodoAcademicoRepository repo2;

	@Override
	public PeriodoAcademicoFor insertarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria, Set<RequisitoFor> requisito, List<MultipartFile> docsPeriodoAcademico, List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, DataException {
	
		if (repo2.getActive().equals(false))
			return repo.insertarConvocatoriaConDocumentos(convocatoria, requisito, docsPeriodoAcademico, docsConvocatoria);
		else
			throw new DataException(PA_ACTIVO);
	}

	@Override
	public PeriodoAcademicoFor actualizarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria,
			Set<RequisitoFor> requisito, List<MultipartFile> docsConvocatoria, DocumentoFor documento)
			throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException {

		return repo1.actualizarConvocatoriaConDocumentos(convocatoria, requisito, docsConvocatoria, documento);
	}
}
