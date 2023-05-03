package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Modulo;
import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ModuloRepository;
import epntech.cbdmq.pe.servicio.ModuloService;

@Service
public class ModuloServiceImpl implements ModuloService {
    @Autowired
    private ModuloRepository repo;

    @Override
    public Modulo save(Modulo obj) throws DataException {
    	if(obj.getEtiqueta().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Modulo> objGuardado = repo.findByEtiquetaIgnoreCase(obj.getEtiqueta());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(obj);
    }

    @Override
    public List<Modulo> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    @Override
    public Modulo update(Modulo objActualizado) throws DataException {
    	Optional<Modulo> objGuardado = repo.findByEtiquetaIgnoreCase(objActualizado.getEtiqueta());
    	if (objGuardado.isPresent()&& !objGuardado.get().getCod_modulo().equals(objActualizado.getCod_modulo())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(objActualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Modulo> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer codigo) {
        // TODO Auto-generated method stub
        repo.deleteById(codigo);
    }
}
