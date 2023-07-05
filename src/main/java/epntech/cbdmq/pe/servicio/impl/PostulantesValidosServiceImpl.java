package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_PRUEBAS;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulantesValidosRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
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
	@Autowired
	private PruebaDetalleRepository pruebaDetalleRepository;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;

	@Override
	public List<PostulantesValidos> getPostulantesValidos() {
		return repo.getPostulantesValidos();
	}

	@Override
	@Async
	public void notificar(String mensaje, String prueba, Date fechaIni, Date fechaFin, LocalTime hora,
			Integer codPrueba) throws MessagingException, Exception {
		List<PostulantesValidos> lista = new ArrayList<>();
		// try {

		Optional<PruebaDetalle> pruebaDetalle = pruebaDetalleRepository
				.findByCodSubtipoPruebaAndCodPeriodoAcademico(codPrueba, periodoAcademicoRepository.getPAActive());
		if (pruebaDetalle.isPresent() && pruebaDetalle.get().getOrdenTipoPrueba().equals(1)) {
			lista = repo.getPostulantesValidos();
		} else if (pruebaDetalle.isPresent()) {
			lista = repo.get_approved_by_test(codPrueba);
		}
		//System.out.println("lista: " + lista.size());
		for (PostulantesValidos postulantesValidos : lista) {
			String msg = String.format(mensaje, postulantesValidos.getIdPostulante(), prueba, fechaIni, fechaFin, hora);

			emailService.enviarEmail(postulantesValidos.getCorreoPersonal(), EMAIL_SUBJECT_PRUEBAS, msg);
		}
		/*
		 * }catch(Exception ex) { System.out.println("error: " + ex.getMessage()); throw
		 * new Exception(ex.getMessage()); }
		 */
	}

	@Override
	public void onInitResultado(List<PostulantesValidos> obj, Integer prueba) {
		List<ResultadoPruebas> resultadoPruebas = new ArrayList<>();

		for (PostulantesValidos postulantesValidos : obj) {

			ResultadoPruebas pruebas = new ResultadoPruebas();
			pruebas.setCodPostulante(postulantesValidos.getCodPostulante());
			pruebas.setCodPruebaDetalle(prueba);
			pruebas.setEstado("ACTIVO");
			resultadoPruebas.add(pruebas);
		}

		resultadoPruebasRepository.saveAll(resultadoPruebas);
	}

	@Override
	public Page<PostulantesValidos> getAllPaginado(Pageable pageable, Integer codPrueba) throws Exception {
		// TODO Auto-generated method stub
		// return repo.getPostulantesValidosPaginado(pageable);
		return repo.get_approved_by_test(pageable, codPrueba);
	}

	@Override
	public List<PostulantesValidos> getPostulantesAprovadosPrueba(Integer prueba) {
		// TODO Auto-generated method stub
		return repo.get_approved_by_test(prueba);
	}

}
