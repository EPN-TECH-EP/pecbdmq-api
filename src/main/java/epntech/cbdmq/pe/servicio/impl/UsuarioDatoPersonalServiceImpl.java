package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.CEDULA_NO_EXISTE;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.UsuarioDatoPersonalRepository;
import epntech.cbdmq.pe.servicio.UsuarioDatoPersonalService;

@Service
public class UsuarioDatoPersonalServiceImpl implements UsuarioDatoPersonalService {

	@Autowired
	private UsuarioDatoPersonalRepository repo;
	
	@Override
	public Set<UsuarioDatoPersonal> getUsuarios() {
		// TODO Auto-generated method stub
		return repo.getUsuarios();
	}

	@Override
	public UsuarioDatoPersonal getByCedula(String cedula) throws DataException {
		// TODO Auto-generated method stub
		if(repo.getByCedula(cedula) == null)
			throw new DataException(CEDULA_NO_EXISTE + ": " + cedula);
		
		return repo.getByCedula(cedula);
	}

}
