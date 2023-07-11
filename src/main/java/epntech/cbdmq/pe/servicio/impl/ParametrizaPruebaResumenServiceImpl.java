package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ParametrizaPruebaResumen;
import epntech.cbdmq.pe.dominio.util.ParametrizaPruebaResumenDatos;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ParametrizaPruebaResumenRepository;
import epntech.cbdmq.pe.servicio.ParametrizaPruebaResumenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ParametrizaPruebaResumenServiceImpl implements ParametrizaPruebaResumenService {

	
	@Autowired
	private ParametrizaPruebaResumenRepository repo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public ParametrizaPruebaResumen save(ParametrizaPruebaResumen obj) throws DataException {
		LocalDate fechaActual = LocalDate.now();
		obj.setFechaCreacion(fechaActual);
		obj.setEstado("ACTIVO");
		
		return repo.save(obj);
	}

	@Override
	public List<ParametrizaPruebaResumen> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<ParametrizaPruebaResumen> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public ParametrizaPruebaResumen update(ParametrizaPruebaResumen objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
		
	}
	
	@Override
	public List<ParametrizaPruebaResumenDatos> listarTodosVigentesConDatosSubtipoPrueba(){
		Query q = this.entityManager.createNativeQuery("select\n"
				+ "	gppr.cod_parametriza_prueba_resumen,\n"
				+ "	gppr.fecha_creacion,\n"
				+ "	gppr.fecha_inicio,\n"
				+ "	gppr.fecha_fin,\n"
				+ "	gppr.descripcion,\n"
				+ "	gppr.estado,\n"
				+ "	gppr.cod_subtipo_prueba,\n"
				+ "	gsp.nombre as subtipo_prueba_nombre,\n"
				+ "	gtp.tipo_prueba as tipo_prueba_nombre\n"
				+ "from\n"
				+ "	cbdmq.gen_parametriza_prueba_resumen gppr,\n"
				+ "	cbdmq.gen_subtipo_prueba gsp ,\n"
				+ "	cbdmq.gen_tipo_prueba gtp\n"
				+ "where\n"
				+ "	current_date between gppr.fecha_inicio and gppr.fecha_fin\n"
				+ "	and gppr.cod_subtipo_prueba = gsp.cod_subtipo_prueba\n"
				+ "	and gtp.cod_tipo_prueba = gsp.cod_tipo_prueba\n"
				+ "order by \n"
				+ "	gtp.tipo_prueba,\n"
				+ "	gsp.nombre", ParametrizaPruebaResumenDatos.class);
		
		List<ParametrizaPruebaResumenDatos> lista = q.getResultList();
		return lista;
		
	}

}
