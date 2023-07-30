package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.EspecializacionConst.NO_ELIMINAR_TIPO_CURSO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.repositorio.admin.especializacion.TipoCursoRepository;
import epntech.cbdmq.pe.servicio.especializacion.TipoCursoService;

@Service
public class TipoCursoServiceImpl implements TipoCursoService {

	@Autowired
	private CatalogoCursoRepository catalogoCursoRepository;
	@Autowired
	private TipoCursoRepository tipoCursoRepository;

	@Override
	public TipoCurso save(TipoCurso tipoCurso) {
		if (tipoCurso.getNombreTipoCurso().trim().isEmpty())
			throw new BusinessException(REGISTRO_VACIO);
		Optional<TipoCurso> objGuardado = tipoCursoRepository
				.findByNombreTipoCursoIgnoreCase(tipoCurso.getNombreTipoCurso());
		if (objGuardado.isPresent()) {
			throw new BusinessException(REGISTRO_YA_EXISTE);
		}

		return tipoCursoRepository.save(tipoCurso);
	}

	@Override
	public TipoCurso update(TipoCurso tipoCursoActualizado) {
		TipoCurso tipoCurso= tipoCursoRepository.findById(tipoCursoActualizado.getCodTipoCurso())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		tipoCurso.setNombreTipoCurso(tipoCursoActualizado.getNombreTipoCurso().toUpperCase());
		tipoCurso.setEstado(tipoCursoActualizado.getEstado());
		tipoCurso.setCodTipoCurso(tipoCursoActualizado.getCodTipoCurso());

		if (tipoCursoActualizado.getNombreTipoCurso() != null) {
			Optional<TipoCurso> objGuardado = tipoCursoRepository
					.findByNombreTipoCursoIgnoreCase(tipoCursoActualizado.getNombreTipoCurso());
			if (objGuardado.isPresent()
					&& !objGuardado.get().getCodTipoCurso().equals(tipoCursoActualizado.getCodTipoCurso())) {
				throw new BusinessException(REGISTRO_YA_EXISTE);
			}
		}
		return tipoCursoRepository.save(tipoCurso);
	}

	@Override
	public TipoCurso getById(Long codTipoCurso) {
		return tipoCursoRepository.findById(codTipoCurso)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public List<TipoCurso> getAll() {
		return tipoCursoRepository.findAll();
	}

	@Override
	public void delete(Long codTipoCurso) {
		TipoCurso tipoCurso= tipoCursoRepository.findById(codTipoCurso)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		List<CatalogoCurso> catalogosActivos = catalogoCursoRepository.findByCodTipoCurso(tipoCurso.getCodTipoCurso().intValue());
		if (!catalogosActivos.isEmpty()) {
			throw new BusinessException(NO_ELIMINAR_TIPO_CURSO);
		}

		tipoCursoRepository.deleteById(tipoCurso.getCodTipoCurso());
	}

}
