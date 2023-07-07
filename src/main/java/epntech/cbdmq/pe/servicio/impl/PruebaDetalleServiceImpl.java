package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.MensajesConst;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
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

	// TODO eliminar comentario
	@Override
	public Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo) {

		return pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subtipo, periodo);
	}

	@Override
	public PruebaDetalle save(PruebaDetalle obj) throws DataException {

		// obtiene el periodo academico activo
		Integer codPeriodoAcademico = this.periodoAcademicoRepository.getPAActive();
		
		if (codPeriodoAcademico == null) {
			throw new DataException(MensajesConst.NO_PERIODO_ACTIVO);
		} else {
			obj.setCodPeriodoAcademico(codPeriodoAcademico);
		}
		
		return pruebaDetalleRepository.save(obj);
	}

	@Override
	public List<PruebaDetalle> getAll() {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.findAll();
	}

	@Override
	public Optional<PruebaDetalle> getById(int id) {
		// TODO Auto-generated method stub
		return pruebaDetalleRepository.findById(id);
	}

	@Override
	public PruebaDetalle update(PruebaDetalle objActualizado) {		
		return pruebaDetalleRepository.save(objActualizado);
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
		//return this.pruebaDetalleRepository.listarTodosConDatosSubTipoPrueba();
		
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
				+ "	gtp.tipo_prueba as tipo_prueba_nombre  "
				+ "from "
				+ "	cbdmq.gen_prueba_detalle gpd, "
				+ "	cbdmq.gen_subtipo_prueba gsp, "
				+ "	cbdmq.gen_tipo_prueba gtp "
				+ "where "
				+ "	gpd.cod_subtipo_prueba = gsp.cod_subtipo_prueba"
				+ " and gsp.cod_tipo_prueba = gtp.cod_tipo_prueba and gpd.cod_periodo_academico = cbdmq.get_pa_activo() order by gpd.orden_tipo_prueba", PruebaDetalleDatos.class);
		
		List<PruebaDetalleDatos> lista = q.getResultList();
		return lista;
	}
}
