package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.repositorio.admin.DelegadoDAORepository;
import epntech.cbdmq.pe.repositorio.admin.DelegadoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.DelegadoService;
import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

@Service
public class DelegadoServiceImpl implements DelegadoService {

	@Autowired
	private DelegadoRepository repo;
	@Autowired
	private DelegadoDAORepository repoDAO;
	@Autowired
	private PeriodoAcademicoRepository repoPA;

	@Override
	public Delegado save(Delegado obj) throws DataException {
		PeriodoAcademico periodoAcademico = new PeriodoAcademico();
		periodoAcademico = repoPA.getPeriodoAcademicoActivo();
		
		obj.setCodPeriodoAcademico(periodoAcademico.getCodigo());
		
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
