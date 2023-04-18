package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.RolUsuario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.RolUsuarioRepository;
import epntech.cbdmq.pe.servicio.RolUsuarioService;

@Service
@Transactional
public class RolUsuarioImpl implements RolUsuarioService {

	private RolUsuarioRepository rolUsuarioRepository;

	@Autowired
	public RolUsuarioImpl(RolUsuarioRepository rolUsuarioRepository) {
		this.rolUsuarioRepository = rolUsuarioRepository;
	}

	@Override
	public List<RolUsuario> getAll() {
		return this.rolUsuarioRepository.findAll();
	}

	@Override
	public List<RolUsuario> getAllByUsuario(Long codUsuario) {
		return this.rolUsuarioRepository.findByCodUsuario(codUsuario);
	}

	@Override
	public RolUsuario save(RolUsuario obj) throws DataException {
		return this.rolUsuarioRepository.save(obj);
	}

	@Override
	public RolUsuario update(RolUsuario objActualizado) throws DataException {
		return this.save(objActualizado);
	}

	@Override
	public void delete(RolUsuario rolUsuario) throws DataException {
		Optional<RolUsuario> ru = this.rolUsuarioRepository.findById(rolUsuario.getRolUsuarioId());
		if (ru.isPresent()) {
			this.rolUsuarioRepository.delete(ru.get());
		}

	}

	@Override
	public void deleteAllByRolUsuarioId_codUsuario(Long codUsuario) {
		this.rolUsuarioRepository.deleteAllByRolUsuarioId_CodUsuario(codUsuario);

	}

	@Override
	public List<RolUsuario> saveAll(Iterable<RolUsuario> entities) {
		return this.rolUsuarioRepository.saveAll(entities);
	}

	@Override
	public void deleteAndSave(Iterable<RolUsuario> entities) {
		
		// primero, eliminar todas las entidades asociadas al usuario
				if (entities != null && entities.iterator().hasNext()) {
					RolUsuario mr = entities.iterator().next();
					
					this.deleteAllByRolUsuarioId_codUsuario(mr.getRolUsuarioId().getCodUsuario());
					
					// luego, registrar la configuración recibida en la lista
					this.saveAll(entities);
					
				}

	}

}
