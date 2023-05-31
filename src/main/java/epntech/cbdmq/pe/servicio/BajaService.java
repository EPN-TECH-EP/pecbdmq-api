package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface BajaService {
	Baja save(Baja obj, @RequestParam List<MultipartFile> archivos) throws DataException, ArchivoMuyGrandeExcepcion, IOException;

    List<Baja> getAll();

    Optional<Baja> getById(Integer codigo);

    Baja update(Baja objActualizado, @RequestParam List<MultipartFile> archivos)throws DataException, ArchivoMuyGrandeExcepcion, IOException;

    void delete(Integer codigo);
    
    Estudiante darDeBaja(Estudiante obj) throws DataException;
}
