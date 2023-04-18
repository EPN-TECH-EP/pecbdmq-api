package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.InscripcionForRepository;
import epntech.cbdmq.pe.repositorio.admin.InscripcionRepository;
import epntech.cbdmq.pe.servicio.InscripcionForService;
import jakarta.mail.MessagingException;


@Service
public class InscripcionForServiceImpl implements InscripcionForService {

	@Autowired
	private InscripcionForRepository repo;
	
	@Autowired
	private InscripcionRepository repo1;

	@Override
	public Integer insertarInscripcionConDocumentos(InscripcionFor inscripcion,
			List<MultipartFile> docsInscripcion) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException {
		
		if(repo1.findByCorreoPersonalIgnoreCase(inscripcion.getCorreoPersonal()).isPresent())
			return -1;
		if(repo1.findOneByCedula(inscripcion.getCedula()).isPresent())
			return -2;
		else
			return repo.insertarInscripcionConDocumentos(inscripcion, docsInscripcion);
	}

	@Override
	public Optional<InscripcionFor> getInscripcionById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo1.findById(codigo);
	}
}
