package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.TipoCursoRepository;
import epntech.cbdmq.pe.servicio.especializacion.TipoCursoService;

@Service
public class TipoCursoServiceImpl implements TipoCursoService {

	@Autowired
	private TipoCursoRepository tipoCursoRepository;

	@Override
	public TipoCurso save(TipoCurso tipoCurso) throws DataException {
		if (tipoCurso.getNombreTipoCurso().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoCurso> objGuardado = tipoCursoRepository
				.findByNombreTipoCursoIgnoreCase(tipoCurso.getNombreTipoCurso());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return tipoCursoRepository.save(tipoCurso);
	}

	@Override
	public TipoCurso update(TipoCurso tipoCursoActualizado) throws DataException {
		if (tipoCursoActualizado.getNombreTipoCurso() != null) {
			Optional<TipoCurso> objGuardado = tipoCursoRepository
					.findByNombreTipoCursoIgnoreCase(tipoCursoActualizado.getNombreTipoCurso());
			if (objGuardado.isPresent()
					&& !objGuardado.get().getCodTipoCurso().equals(tipoCursoActualizado.getCodTipoCurso())) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}
		return tipoCursoRepository.save(tipoCursoActualizado);
	}

	@Override
	public Optional<TipoCurso> getById(Long codTipoCurso) {
		// TODO Auto-generated method stub
		return tipoCursoRepository.findById(codTipoCurso);
	}

	@Override
	public List<TipoCurso> getAll() {
		// TODO Auto-generated method stub
		return tipoCursoRepository.findAll();
	}

	@Override
	public void delete(Long codTipoCurso) throws DataException {
		Optional<TipoCurso> tipoCursoOptional; 
		tipoCursoOptional = tipoCursoRepository.findById(codTipoCurso);
		
		if(tipoCursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		tipoCursoRepository.deleteById(codTipoCurso);

	}

}
