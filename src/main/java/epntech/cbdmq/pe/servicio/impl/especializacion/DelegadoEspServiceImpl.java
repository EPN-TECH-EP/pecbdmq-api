package epntech.cbdmq.pe.servicio.impl.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoUtilEsp;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.DelegadoEspRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.DelegadoEspUtilRepository;
import epntech.cbdmq.pe.servicio.especializacion.DelegadoEspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class DelegadoEspServiceImpl implements DelegadoEspService {

	@Autowired
	private DelegadoEspRepository delegadoEspRepository;
	@Autowired
	private DelegadoEspUtilRepository delegadoEspUtilRepository;

	@Override
	public DelegadoEsp save(DelegadoEsp obj) {
		Optional<DelegadoEsp> delegadoEspOptional = delegadoEspRepository.findByCodUsuario(obj.getCodUsuario());

		if (delegadoEspOptional.isPresent()) {
			throw new BusinessException(REGISTRO_YA_EXISTE);
		}

		return delegadoEspRepository.save(obj);
	}

	@Override
	public List<DelegadoEsp> getAll() {
		return delegadoEspRepository.findAll();
	}

	@Override
	public void delete(Long codDelegadoEsp) {
		DelegadoEsp delegadoEsp = delegadoEspRepository.findById(codDelegadoEsp)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		delegadoEspRepository.delete(delegadoEsp);
	}

	@Override
	public DelegadoEsp getByIdUsuario(int codUsuario) {
		return delegadoEspRepository.findByCodUsuario(codUsuario)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public Boolean isUsuarioDelegado(int codUsuario) {
		return delegadoEspRepository.findByCodUsuario(codUsuario).isPresent();
	}

	@Override
	public List<DelegadoUtilEsp> delegado() {
		return delegadoEspUtilRepository.findDelegados();
	}

}
