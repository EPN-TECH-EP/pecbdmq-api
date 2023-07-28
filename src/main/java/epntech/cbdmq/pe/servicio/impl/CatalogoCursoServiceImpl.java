package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.EspecializacionConst.TIPO_CURSO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.repositorio.admin.especializacion.TipoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursoRepository;
import epntech.cbdmq.pe.servicio.CatalogoCursoService;

@Service
public class CatalogoCursoServiceImpl implements CatalogoCursoService {
	@Autowired
	private CatalogoCursoRepository repo;

	@Autowired
	private TipoCursoRepository tipoCursoRepository;

	@Override
	public CatalogoCurso save(CatalogoCurso obj) throws DataException {
		TipoCurso tipoCurso = tipoCursoRepository.findById(obj.getCodTipoCurso().longValue())
				.orElseThrow(() -> new BusinessException(TIPO_CURSO_NO_EXISTE));

		obj.setCodTipoCurso(tipoCurso.getCodTipoCurso().intValue());
		obj.setNombreCatalogoCurso(obj.getNombreCatalogoCurso().toUpperCase());
		
		Optional<CatalogoCurso> objGuardado = repo.findByNombreCatalogoCursoIgnoreCase(obj.getNombreCatalogoCurso());
				if (objGuardado.isPresent()) {

					// valida si existe eliminado
					CatalogoCurso stp = objGuardado.get();
					if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
						stp.setEstado(EstadosConst.ACTIVO);
						return repo.save(stp);
					} else {
					throw new DataException(REGISTRO_YA_EXISTE);
					}

				}
		
				return repo.save(obj);
	}
	@Override
	public List<CatalogoCurso> getAll() {
		return repo.findAll();
	}

	@Override
	public List<CatalogoCurso> getByCodigoTipoCurso(Integer codigoTipoCurso) {
		TipoCurso tipoCurso = tipoCursoRepository.findById(codigoTipoCurso.longValue())
				.orElseThrow(() -> new BusinessException(TIPO_CURSO_NO_EXISTE));
		return repo.findByCodTipoCurso(tipoCurso.getCodTipoCurso().intValue());
	}

	@Override
	public Optional<CatalogoCurso> getById(int id) {
		return repo.findById(id);
	}

	@Override
	public CatalogoCurso update(CatalogoCurso objActualizado) {
		objActualizado.setNombreCatalogoCurso(objActualizado.getNombreCatalogoCurso().toUpperCase());
		CatalogoCurso catalogoCurso = repo.findById(objActualizado.getCodCatalogoCursos())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		validarCatalogoCurso(objActualizado);

		catalogoCurso.setDescripcionCatalogoCurso(objActualizado.getDescripcionCatalogoCurso());
		catalogoCurso.setNombreCatalogoCurso(objActualizado.getNombreCatalogoCurso());
		catalogoCurso.setCodTipoCurso(objActualizado.getCodTipoCurso());
		catalogoCurso.setEstado(objActualizado.getEstado());

		return repo.save(catalogoCurso);
	}

	private void validarCatalogoCurso(CatalogoCurso objActualizado) {
		Optional<CatalogoCurso> objGuardado = repo
				.findByNombreCatalogoCursoIgnoreCase(objActualizado.getNombreCatalogoCurso());
		if (objGuardado.isPresent()
				&& !objGuardado.get().getCodCatalogoCursos().equals(objActualizado.getCodCatalogoCursos())) {
			throw new BusinessException(REGISTRO_YA_EXISTE);
		}
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

}