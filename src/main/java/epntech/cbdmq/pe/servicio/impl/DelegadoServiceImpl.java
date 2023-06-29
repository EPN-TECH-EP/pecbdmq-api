package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.repositorio.admin.DelegadoDAORepository;
import epntech.cbdmq.pe.repositorio.admin.DelegadoRepository;
import epntech.cbdmq.pe.repositorio.admin.DelegadoUtilRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.DelegadoService;
import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.util.DelegadoPK;
import epntech.cbdmq.pe.dominio.util.DelegadoUtil;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

@Service
public class DelegadoServiceImpl implements DelegadoService {

	@Autowired
	private DelegadoRepository repo;
	
	@ Autowired
	private DelegadoUtilRepository delegadoUtilRepository; 
	
	@Autowired
	private DelegadoDAORepository repoDAO;
	@Autowired
	private PeriodoAcademicoRepository repoPA;

	@Override
	public Delegado save(Delegado obj) throws DataException {
		PeriodoAcademico periodoAcademico = new PeriodoAcademico();
		periodoAcademico = repoPA.getPeriodoAcademicoActivo();
		
		Optional<Delegado> delegado=repo.findByCodUsuarioAndCodPeriodoAcademico(obj.getCodUsuario(), obj.getCodPeriodoAcademico());
		if(delegado.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);
		
		obj.setCodPeriodoAcademico(periodoAcademico.getCodigo());

		
		return repo.save(obj);
	}

	@Override
	public List<Delegado> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void delete(int codDelegado) throws DataException {


		PeriodoAcademico periodoAcademico = repoPA.getPeriodoAcademicoActivo();
		repoDAO.delete(codDelegado, periodoAcademico.getCodigo());
	}

	@Override
	public Optional<Delegado> getByIdUsuario(int codUsuario) throws DataException {
		Optional<Delegado> delegado=repo.findBycodUsuario(codUsuario);
		if(!delegado.isPresent())
			throw new DataException(REGISTRO_NO_EXISTE);
		return delegado;
	}

	@Override
	public Boolean isUsuarioDelegado(int codUsuario) throws DataException {
		Optional<Delegado> delegado=repo.delegadoByUser(codUsuario);
		if (delegado.isPresent()) {
			return true;
		}
		return false;

	}

	@Override
	public List<DelegadoUtil> delegado() {
		// TODO Auto-generated method stub
		return delegadoUtilRepository.getDelegados();
	}

	

}
