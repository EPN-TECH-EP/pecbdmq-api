package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CURSO_REPROBADO;
import static epntech.cbdmq.pe.constante.EspecializacionConst.NO_SUBTIPO_PRUEBA;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursoRepository;
import epntech.cbdmq.pe.repositorio.admin.SubTipoPruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.InscripcionEspRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.TipoCursoRepository;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.constante.MensajesConst;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleOrden;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class PruebaDetalleServiceImpl implements PruebaDetalleService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PruebaDetalleRepository pruebaDetalleRepository;

	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;

	@Autowired
	private SubTipoPruebaRepository subTipoPruebaRepository;
	@Autowired
	private InscripcionEspRepository inscripcionEspRepository;
	@Autowired
	private DatoPersonalService dpSvc;
	@Autowired
	private ParametroRepository parametroRepository;
	@Autowired
	private CursoService cursoSc;
	@Autowired
	private TipoCursoRepository tipoCursoRepository;
	@Autowired
	private CatalogoCursoRepository catalogoCursoRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private TipoPruebaService tipoPruebaSvc;

	@Override
	public Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo) {

		return pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subtipo, periodo);
	}

	@Override
	public Optional<PruebaDetalle> findByCodCursoEspecializacionAndCodSubtipoPrueba(Integer codCursoEspecializacion, Integer subtipo) {
		return this.pruebaDetalleRepository.findByCodCursoEspecializacionAndCodSubtipoPrueba(codCursoEspecializacion, subtipo);
	}

	@Override
	public PruebaDetalle save(PruebaDetalle obj) {
		SubTipoPrueba subTipoPrueba = subTipoPruebaRepository.findById(obj.getCodSubtipoPrueba())
				.orElseThrow(() -> new BusinessException(NO_SUBTIPO_PRUEBA));

		if (obj.getCodCursoEspecializacion() == null) {
			// obtiene el periodo academico activo
			Integer codPeriodoAcademico = Optional.ofNullable(this.periodoAcademicoRepository.getPAActive())
					.orElseThrow(() -> new BusinessException(MensajesConst.NO_PERIODO_ACTIVO));
			// busca si existe eliminado y lo reactiva
			Optional<PruebaDetalle> pruebaDetalleExistenteOpt = this.pruebaDetalleRepository
					.findByCodSubtipoPruebaAndCodPeriodoAcademico(subTipoPrueba.getCodSubtipoPrueba(), codPeriodoAcademico);

			if (pruebaDetalleExistenteOpt.isPresent()) {
				PruebaDetalle pruebaDetalleExistente = pruebaDetalleExistenteOpt.get();

				if (pruebaDetalleExistente.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {

					obj.setCodPruebaDetalle(pruebaDetalleExistente.getCodPruebaDetalle());
					obj.setEstado(EstadosConst.ACTIVO);
					obj.setCodPeriodoAcademico(pruebaDetalleExistente.getCodPeriodoAcademico());
					obj.setCodCursoEspecializacion(pruebaDetalleExistente.getCodCursoEspecializacion());

					/*
					 *
					 *
					 * obj.setDescripcionPrueba(pruebaDetalleExistente.getDescripcionPrueba());
					 *
					 * obj.setFechaFin(pruebaDetalleExistente.getFechaFin());
					 * obj.setFechaInicio(pruebaDetalleExistente.getFechaInicio());
					 * obj.setHora(pruebaDetalleExistente.getHora());
					 * obj.setOrdenTipoPrueba(pruebaDetalleExistente.getOrdenTipoPrueba());
					 * obj.setPuntajeMaximo(pruebaDetalleExistente.getPuntajeMaximo());
					 * obj.setPuntajeMinimo(pruebaDetalleExistente.getPuntajeMinimo());
					 * obj.setTienePuntaje(pruebaDetalleExistente.getTienePuntaje());
					 */
				}

			} else {
				obj.setCodPeriodoAcademico(codPeriodoAcademico);
			}
		}

		return pruebaDetalleRepository.save(obj);
	}

	@Override
	public List<PruebaDetalle> getAll() {
		return pruebaDetalleRepository.findAll();
	}

	@Override
	public PruebaDetalle getById(int id) {
		return pruebaDetalleRepository.findById(id)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public PruebaDetalle update(PruebaDetalle objActualizado) {
		PruebaDetalle pruebaDetalle = pruebaDetalleRepository.findById(objActualizado.getCodPruebaDetalle())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		pruebaDetalle.setDescripcionPrueba(objActualizado.getDescripcionPrueba());
		pruebaDetalle.setFechaInicio(objActualizado.getFechaInicio());
		pruebaDetalle.setFechaFin(objActualizado.getFechaFin());
		pruebaDetalle.setHora(objActualizado.getHora());
		pruebaDetalle.setEstado(objActualizado.getEstado());
		pruebaDetalle.setPuntajeMinimo(objActualizado.getPuntajeMinimo());
		pruebaDetalle.setPuntajeMaximo(objActualizado.getPuntajeMaximo());
		pruebaDetalle.setTienePuntaje(objActualizado.getTienePuntaje());

		return pruebaDetalle.getCodCursoEspecializacion() == null
				? this.save(pruebaDetalle)
				: pruebaDetalleRepository.save(pruebaDetalle);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = pruebaDetalleRepository.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			pruebaDetalleRepository.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}

	@Override
	public List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba() {
		// return this.pruebaDetalleRepository.listarTodosConDatosSubTipoPrueba();

		Query q =

				this.entityManager.createNativeQuery("select "
						+ "	gpd.cod_prueba_detalle, "
						+ "	gpd.descripcion_prueba, "
						+ "	gpd.fecha_inicio, "
						+ "	gpd.fecha_fin, "
						+ "	gpd.hora, "
						+ "	gpd.estado, "
						+ "	gpd.cod_periodo_academico, "
						+ "	gpd.cod_curso_especializacion, "
						+ "	gpd.cod_subtipo_prueba, "
						+ "	gpd.orden_tipo_prueba, "
						+ "	gpd.puntaje_minimo, "
						+ "	gpd.puntaje_maximo, "
						+ "	gpd.tiene_puntaje, "
						+ "	gsp.nombre as sub_tipo_prueba_nombre, "
						+ "	gtp.tipo_prueba as tipo_prueba_nombre, gtp.es_fisica as es_fisica  "
						+ "from "
						+ "	cbdmq.gen_prueba_detalle gpd, "
						+ "	cbdmq.gen_subtipo_prueba gsp, "
						+ "	cbdmq.gen_tipo_prueba gtp "
						+ "where "
						+ "	gpd.cod_subtipo_prueba = gsp.cod_subtipo_prueba"
						+ " and gsp.cod_tipo_prueba = gtp.cod_tipo_prueba and gpd.cod_periodo_academico = cbdmq.get_pa_activo() "
						+ " and gpd.estado <> 'ELIMINADO' order by gpd.orden_tipo_prueba",
						PruebaDetalleDatos.class);

		List<PruebaDetalleDatos> lista = q.getResultList();
		return lista;
	}

	@Override
	public List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba(Integer codCurso) {
		// return this.pruebaDetalleRepository.listarTodosConDatosSubTipoPrueba();

		Query q =

				this.entityManager.createNativeQuery("select "
								+ "	gpd.cod_prueba_detalle, "
								+ "	gpd.descripcion_prueba, "
								+ "	gpd.fecha_inicio, "
								+ "	gpd.fecha_fin, "
								+ "	gpd.hora, "
								+ "	gpd.estado, "
								+ "	gpd.cod_periodo_academico, "
								+ "	gpd.cod_curso_especializacion, "
								+ "	gpd.cod_subtipo_prueba, "
								+ "	gpd.orden_tipo_prueba, "
								+ "	gpd.puntaje_minimo, "
								+ "	gpd.puntaje_maximo, "
								+ "	gpd.tiene_puntaje, "
								+ "	gsp.nombre as sub_tipo_prueba_nombre, "
								+ "	gtp.tipo_prueba as tipo_prueba_nombre, gtp.es_fisica as es_fisica  "
								+ "from "
								+ "	cbdmq.gen_prueba_detalle gpd, "
								+ "	cbdmq.gen_subtipo_prueba gsp, "
								+ "	cbdmq.gen_tipo_prueba gtp "
								+ "where "
								+ "	gpd.cod_subtipo_prueba = gsp.cod_subtipo_prueba"
								+ " and gsp.cod_tipo_prueba = gtp.cod_tipo_prueba and gpd.cod_curso_especializacion = :codCurso"
								+ " and gpd.estado <> 'ELIMINADO' order by gpd.orden_tipo_prueba",
						PruebaDetalleDatos.class);

		q.setParameter("codCurso", codCurso);

		List<PruebaDetalleDatos> lista = q.getResultList();
		return lista;
	}


	@Override
	public String getTipoResultado(int codSubtipoPrueba){
		return pruebaDetalleRepository.getTipoResultado(codSubtipoPrueba);
}

	@Override
	public String getTipoResultadoCurso(int codSubtipoPrueba, int codCurso){
		return pruebaDetalleRepository.getTipoResultadoCurso(codSubtipoPrueba, codCurso);
	}

	@Override
	public Boolean reordenar(List<PruebaDetalleOrden> listaOrden) throws DataException {
		Boolean retval = false;

		// procesa la lista con el orden especificado
		for (PruebaDetalleOrden pruebaDetalleOrden : listaOrden) {

			// busca la prueba por id
			Optional<PruebaDetalle> pruebaDetalleOpt = this.pruebaDetalleRepository
					.findById(pruebaDetalleOrden.getCodPruebaDetalle());

			if (pruebaDetalleOpt.isPresent()) {
				PruebaDetalle pruebaDetalle = pruebaDetalleOpt.get();

				if (pruebaDetalle.getOrdenTipoPrueba() != pruebaDetalleOrden.getOrdenTipoPrueba()) {
					pruebaDetalle.setOrdenTipoPrueba(pruebaDetalleOrden.getOrdenTipoPrueba());

					this.pruebaDetalleRepository.save(pruebaDetalle);
				}

			} else {
				throw new DataException(REGISTRO_NO_EXISTE);
			}
		}

		return retval;
	}

	@Override
	@Async
	public void notificarPruebaDetalle(Long codPruebaDetalle) throws DataException {
		Optional<PruebaDetalle> pruebaDetalleOpt = this.pruebaDetalleRepository.findById(codPruebaDetalle.intValue());

		if (pruebaDetalleOpt.isPresent()) {
			Parametro parametro = parametroRepository.findByNombreParametro("especializacion.notificacion.prueba")
					.orElseThrow(() -> new BusinessException(NO_PARAMETRO));
			Curso curso= cursoSc.getById(pruebaDetalleOpt.get().getCodCursoEspecializacion().longValue());
			CatalogoCurso catalogoCurso= catalogoCursoRepository.findById(curso.getCodCatalogoCursos().intValue()).get();
			TipoCurso tipoCurso= tipoCursoRepository.findById(catalogoCurso.getCodTipoCurso().longValue()).get();
			String mensajeCurso=curso.getNombre() +" - "+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso();
			Set<InscripcionDatosEspecializacion> datos = inscripcionEspRepository.getInscripcionesByCursoEstado(pruebaDetalleOpt.get().getCodCursoEspecializacion().longValue(), "%");
			SubTipoPrueba subTipoPrueba= subTipoPruebaRepository.findById(pruebaDetalleOpt.get().getCodSubtipoPrueba()).get();
			TipoPrueba tipoPrueba= tipoPruebaSvc.getById(subTipoPrueba.getCodTipoPrueba()).get();
			for (InscripcionDatosEspecializacion resultadosPruebasDatos : datos) {
				DatoPersonal dato;


				dato = dpSvc.getByCedula(resultadosPruebasDatos.getCedula()).get();


				if (dato == null) {
					throw new DataException("No existe un dato personal asociado");
				}

				try {
					String nombres = dato.getNombre() + " " + dato.getApellido();
					String cuerpoHtml = String.format(parametro.getValor(), nombres,mensajeCurso, subTipoPrueba.getNombre(), tipoPrueba.getTipoPrueba(),tipoPrueba.getEsFisica()? "FÍSICA":"NO FÍSICA", pruebaDetalleOpt.get().getFechaInicio(), pruebaDetalleOpt.get().getFechaFin(),pruebaDetalleOpt.get().getHora());
					String[] destinatarios = {dato.getCorreoPersonal(),dato.getCorreoInstitucional()};
					emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_CURSO_REPROBADO, cuerpoHtml);

				} catch (Exception e) {
				}
			}


		} else {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
	}
}
