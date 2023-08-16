package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProfesionalizacionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;


public class ProfesionalizacionServiceImpl<T extends ProfesionalizacionEntity, U, V extends ProfesionalizacionRepository<T, U>> implements ProfesionalizacionService<T, U> {

    protected V repository;

    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MÁXIMO;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Value("${server.port}")
    public String SERVER_PORT;

    @Value("${eureka.instance.hostname}")
    public String HOSTNAME;
    @Value("${url.descarga.archivos}")
    public String URLDESCARGA;


    public ProfesionalizacionServiceImpl(V repository) {
        this.repository = repository;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(U codigo) {
        return repository.findById(codigo);
    }

    @Override
    public T save(T obj) throws DataException {
        if (obj.getEstado() == null) {
            obj.setEstado(EstadosConst.ACTIVO);
        }
        return repository.save(obj);
    }

    @Override
    public T update(T datosGuardados) throws DataException {
        if (datosGuardados.getEstado() == null) {
            datosGuardados.setEstado(EstadosConst.ACTIVO);
        }
        return repository.save(datosGuardados);
    }

    @Override
    public void delete(U codigo) throws DataException {
        Optional<?> objGuardado = repository.findById(codigo);
        if (objGuardado.isEmpty()) {
            throw new DataException(REGISTRO_NO_EXISTE);
        }
        try {
            repository.deleteById(codigo);
        } catch (Exception e) {
            if (e.getMessage().contains("constraint")) {
                throw new DataException(DATOS_RELACIONADOS);
            }
        }
    }

    public List<DatosFile> guardarArchivo(List<MultipartFile> archivo, String proceso, String id)
            throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;

        resultado = ruta(proceso, id);
        Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        List<DatosFile> lista = new ArrayList<>();

        for (Iterator iterator = archivo.iterator(); iterator.hasNext();) {
            DatosFile documentos = new DatosFile();
            MultipartFile multipartFile = (MultipartFile) iterator.next();
            if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            // LOGGER.info("Archivo guardado: " + resultado +

            documentos.setRuta(resultado + multipartFile.getOriginalFilename());
            documentos.setNombre(multipartFile.getOriginalFilename());
            lista.add(documentos);
        }

        return lista;
    }

    private String ruta(String proceso, String id) {

        String resultado = null;
        resultado = ARCHIVOS_RUTA + proceso + id + "/";
        return resultado;
    }
}
