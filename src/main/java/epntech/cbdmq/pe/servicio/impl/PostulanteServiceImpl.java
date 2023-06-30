package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.POSTULANTE_ASIGNADO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.ESTADO_INVALIDO;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.util.PostulanteDatoPersonal;
import epntech.cbdmq.pe.dominio.util.PostulanteUtil;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PostulanteDPRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulanteRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulanteUtilRepository;
import epntech.cbdmq.pe.servicio.PostulanteService;

@Service
public class PostulanteServiceImpl implements PostulanteService {

	@Autowired
	private PostulanteRepository repo;

	@Autowired
	private PostulanteDPRepository repo1;

	@Autowired
	private PostulanteUtilRepository postulanteUtilRepository;
	@Autowired
	PeriodoAcademicoRepository periodoAcademicoRepository;

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
	public Optional<Postulante> getById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public void delete(Long id) throws DataException {
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
	public List<PostulanteUtil> getPostulantesPaginado(Integer usuario, Pageable pageable) {
		// TODO Auto-generated method stub
		return postulanteUtilRepository.getPostulantesPaginado(usuario, pageable);
	}

	@Override
	public Postulante update(Postulante objActualizado) throws DataException {
		Optional<Postulante> postulante;
		Boolean bandera = false;

		postulante = repo.findById(objActualizado.getCodPostulante());
		if(postulante.isPresent()) {

			if(postulante.get().getEstado().equalsIgnoreCase("PENDIENTE") &&  objActualizado.getEstado().equalsIgnoreCase("ASIGNADO"))
				bandera = true;
			else
				bandera = false;

			// if(bandera) {
			Postulante p = postulante.get();

			//p.setCodDatoPersonal(objActualizado.getCodDatoPersonal());
			//p.setIdPostulante(objActualizado.getIdPostulante());
			//p.setCodPeriodoAcademico(objActualizado.getCodPeriodoAcademico());
			
			p.setEstado(objActualizado.getEstado());
			p.setCodUsuario(objActualizado.getCodUsuario());
			return repo.save(p);
		}
		// else
		// throw new DataException(ESTADO_INVALIDO + " Estado actual: " +
		// postulante.get().getEstado() + ", Estado ingresado: " +
		// objActualizado.getEstado());
//		}
		else
			throw new DataException(REGISTRO_NO_EXISTE + " - " + objActualizado.getCodPostulante());
	}

	@Override
	public void updateState(Integer codpostulante) {
		repo.updateState(codpostulante);

	}

	@Override
	public List<PostulanteUtil> getPostulantesAllPaginado(Pageable pageable) {
		// TODO Auto-generated method stub
		return postulanteUtilRepository.getPostulantesAllPaginadoTodoAsignado(pageable);
	}

	@Override
	public List<Postulante> getMuestra() {
		// TODO Auto-generated method stub
		return repo.getMuestra();
	}

	@Override
	public Postulante updateEstadoMuestra(Postulante objActualizado) throws DataException {
		Optional<Postulante> postulante;
		Boolean bandera = false;

		postulante = repo.findById(objActualizado.getCodPostulante());
		if (postulante.isPresent()) {
			if (postulante.get().getEstado().equalsIgnoreCase("VALIDO")
					&& objActualizado.getEstado().equalsIgnoreCase("ASIGNADO MUESTRA"))
				bandera = true;

			else
				bandera = false;

			if (bandera) {
				Postulante p = postulante.get();

				objActualizado.setCodDatoPersonal(p.getCodDatoPersonal());
				objActualizado.setIdPostulante(p.getIdPostulante());
				objActualizado.setCodPeriodoAcademico(p.getCodPeriodoAcademico());
				return repo.save(objActualizado);
			} else
				throw new DataException(ESTADO_INVALIDO + " Estado actual: " + postulante.get().getEstado()
						+ ", Estado ingresado: " + objActualizado.getEstado());
		} else
			throw new DataException(REGISTRO_NO_EXISTE + " - " + objActualizado.getCodPostulante());
	}

	@Override
	public List<Postulante> getPostulantesAsignadosPaginado(Integer usuario, Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.getPostulantesAsignadosPaginado(usuario, pageable);
	}

	@Override
	public List<PostulanteUtil> getPostulantesAllPaginadoTodo(Pageable pageable) {
		// TODO Auto-generated method stub
		return postulanteUtilRepository.getPostulantesAllPaginadoTodoAsignado(pageable);
	}

	@Override
	public List<Postulante> getPostulantesEstadoPA(String estado) {


		return repo.getPostulantesByEstadoAndCodPeriodoAcademico(estado,periodoAcademicoRepository.getPAActive());
	}

	@Override
	public List<Postulante> getPostulantesMuestraPA() {
		return repo.getPostulantesByEstadoAndCodPeriodoAcademico("MUESTRA",periodoAcademicoRepository.getPAActive());
	}

}
