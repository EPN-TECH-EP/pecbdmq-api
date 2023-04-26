package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.POSTULANTE_ASIGNADO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.util.PostulanteDatoPersonal;
import epntech.cbdmq.pe.dominio.util.PostulanteDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PostulanteDPRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulanteRepository;
import epntech.cbdmq.pe.servicio.PostulanteService;

@Service
public class PostulanteServiceImpl implements PostulanteService {

	@Autowired
	private PostulanteRepository repo;
	
	@Autowired
	private PostulanteDPRepository repo1;
	
	@Override
	public Postulante save(Postulante obj, String proceso) {
		// TODO Auto-generated method stub
		obj.setIdPostulante(repo.getIdPostulante(proceso));
		return repo.save(obj);
	}

	@Override
	public List<Postulante> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Postulante> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public void delete(int id) throws DataException {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	public Optional<PostulanteDatoPersonal> getByCedula(String cedula){
		return repo1.getByCedula(cedula);
	}

	@Override
	public List<Postulante> getPostulantes(Integer usuario) {
		// TODO Auto-generated method stub
		return repo.getPostulantes(usuario);
	}
	
	@Override
	public List<Postulante> getPostulantesPaginado(Integer usuario, Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.getPostulantesPaginado(usuario, pageable);
	}

	@Override
	public Postulante update(Postulante objActualizado) throws DataException {
		Optional<Postulante> postulante;
		
		postulante = repo.findById(objActualizado.getCodPostulante());
		if(postulante.isPresent()) {
			Postulante p = postulante.get();
			if(p.getCodUsuario() == null) {
				objActualizado.setCodDatoPersonal(p.getCodDatoPersonal());
				objActualizado.setIdPostulante(p.getIdPostulante());
				objActualizado.setEstado("ASIGNADO");
				return repo.save(objActualizado);
			}
			else
				throw new DataException(POSTULANTE_ASIGNADO);
		}
		else
			throw new DataException(REGISTRO_NO_EXISTE + " - " + objActualizado.getCodPostulante());
	}

	@Override
	public void updateState(Integer codpostulante) {
		repo.updateState(codpostulante);
		
	}

}
