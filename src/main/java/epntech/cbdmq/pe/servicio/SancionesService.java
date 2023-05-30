package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Sanciones;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface SancionesService {

	Sanciones save(Sanciones obj, MultipartFile archivo) throws DataException, ArchivoMuyGrandeExcepcion, IOException;

    List<Sanciones> getAll();

    Optional<Sanciones> getById(Integer codigo);

    Sanciones update(Sanciones objActualizado, MultipartFile archivo) throws DataException, ArchivoMuyGrandeExcepcion, IOException;

    void delete(Integer codigo);
    
}
