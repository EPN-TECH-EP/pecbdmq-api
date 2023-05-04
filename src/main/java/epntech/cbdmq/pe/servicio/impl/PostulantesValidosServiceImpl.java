package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_PRUEBAS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Modulo;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.repositorio.admin.ModuloRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulantesValidosRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.PostulantesValidosService;
import jakarta.mail.MessagingException;

@Service
public class PostulantesValidosServiceImpl implements PostulantesValidosService {
	
	@Autowired
	private PostulantesValidosRepository repo;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ResultadoPruebasRepository resultadoPruebasRepository;

	@Override
	public List<PostulantesValidos> getPostulantesValidos() {
		return repo.getPostulantesValidos();
	}

	@Override
	@Async
	public void notificar(String mensaje) throws MessagingException {
		List<PostulantesValidos> lista = new ArrayList<>();
		lista = repo.getPostulantesValidos();
		
		for (PostulantesValidos postulantesValidos : lista) {
			String msg = String.format(mensaje, postulantesValidos.getIdPostulante());
			emailService.enviarEmail(postulantesValidos.getCorreoPersonal(), EMAIL_SUBJECT_PRUEBAS, msg);
		}
	}

	@Override
	public void onInitResultado(List<PostulantesValidos> obj, Integer modulo, Integer prueba) {
		List<ResultadoPruebas> resultadoPruebas = new ArrayList<>();
		
		for (PostulantesValidos postulantesValidos : obj) {
			
			ResultadoPruebas pruebas = new ResultadoPruebas();
			pruebas.setCodModulo(modulo);
			pruebas.setCodPostulante(postulantesValidos.getCodPostulante());
			pruebas.setCodPrueba(prueba);
			pruebas.setEstado("ACTIVO");
			resultadoPruebas.add(pruebas);
		}
		
		resultadoPruebasRepository.saveAll(resultadoPruebas);
	}

	@Override
	public Page<PostulantesValidos> getAllPaginado(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return repo.getPostulantesValidosPaginado(pageable);
	}

}
