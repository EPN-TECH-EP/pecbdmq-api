package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.EspecializacionConst.NO_SUBTIPO_PRUEBA;
import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.SubTipoPruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.constante.MensajesConst;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleOrden;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.servicio.PruebaDetalleService;
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

	@Override
	public Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo) {

		return pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subtipo, periodo);
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
	public String getTipoResultado(int codSubtipoPrueba){
		return pruebaDetalleRepository.getTipoResultado(codSubtipoPrueba);
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
}
