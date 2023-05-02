package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.repositorio.admin.DelegadoDAORepository;
import epntech.cbdmq.pe.repositorio.admin.DelegadoRepository;
import epntech.cbdmq.pe.servicio.DelegadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.admin.DelegadoPK;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

@Service
public class DelegadoServiceImpl implements DelegadoService {

	@Autowired
	private DelegadoRepository repo;
	@Autowired
	private DelegadoDAORepository repoDAO;

	@Override
	public Delegado save(Delegado obj) throws DataException {
		
		return repo.save(obj);
	}

	@Override
	public List<Delegado> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void delete(int codUsuario, int codPeriodoAcademico) throws DataException {
		repoDAO.delete(codUsuario, codPeriodoAcademico);
	}

}
