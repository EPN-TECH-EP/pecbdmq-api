package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoPrueba;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.util.MateriaDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.servicio.DocumentoPruebaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;

@Service
public class DocumentoPruebaServiceImpl implements DocumentoPruebaService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;

    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MÁXIMO;
    @Autowired
    DocumentoPruebaRepository repo;

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    PeriodoAcademicoRepository periodoAcademicoRepository;

    @Autowired
    PruebaDetalleRepository repo3;

    private String ruta(String codigo) {

        String resultado = null;
        PeriodoAcademico periodo=periodoAcademicoRepository.getPeriodoAcademicoActivo();

        resultado = ARCHIVOS_RUTA + PATH_PROCESO_FORMACION + periodo.getCodigo() + "/Prueba/" + codigo + "/";

        return resultado;
    }

    @Override
    public List<DocumentoRuta> guardarArchivo( Integer prueba, List<MultipartFile> archivo) throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;

        resultado = ruta(prueba.toString());
        Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        List<DocumentoRuta> lista = new ArrayList<>();
        DocumentoRuta documentos = new DocumentoRuta();
        // Files.copy(archivo.getInputStream(),
        // ruta.resolve(archivo.getOriginalFilename()),
        // StandardCopyOption.REPLACE_EXISTING);
        for (Iterator iterator = archivo.iterator(); iterator.hasNext();) {
            MultipartFile multipartFile = (MultipartFile) iterator.next();
            if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Archivo guardado: " + resultado + multipartFile.getOriginalFilename());
            documentos.setRuta(resultado + multipartFile.getOriginalFilename());
            lista.add(documentos);

            Documento documento = new Documento();
            documento.setEstado("ACTIVO");
            documento.setNombre(multipartFile.getOriginalFilename());
            documento.setRuta(resultado + multipartFile.getOriginalFilename());
            documento = documentoRepository.save(documento);
            System.out.println("documento.getCodigo() " + documento.getCodDocumento());
            System.out.println("prueba " + prueba);

            DocumentoPrueba matdoc = new DocumentoPrueba();
            matdoc.setCodDocumento(documento.getCodDocumento());
            matdoc.setCodPruebaDetalle(prueba);
            repo.save(matdoc);

        }
        return lista;
    }

    @Override
    public void deleteDocumento(Integer prueba, Long codDocumento) throws DataException {
        Optional<Documento> documentoOptional;
        Documento documento = new Documento();

        // System.out.println("id: " + codDocumento);
        documentoOptional = documentoRepository.findById(codDocumento.intValue());
        documento = documentoOptional.get();

        Path ruta = Paths.get(documento.getRuta());

        // System.out.println("ruta: " + ruta);
        if (Files.exists(ruta)) {
            try {
                // System.out.println("ruta" + ruta);
                Files.delete(ruta);
                repo.deleteByCodPruebaDetalleAndCodDocumento(prueba, codDocumento.intValue());
                documentoRepository.deleteById(codDocumento.intValue());

            } catch (Exception e) {

                throw new DataException(e.getMessage());
                // e.printStackTrace();
            }

        }

    }
//TODO: revisar si una prueba puede tener muchos documentos asociados
    @Override
    public MultipartFile getDocumento(Integer codPrueba, Long codDocumento) throws DataException, FileNotFoundException {
        Documento archivo = documentoRepository.findById(codDocumento.intValue()).orElse(null);
        if (archivo == null) {
          throw new DataException(ARCHIVO_NO_EXISTE);
        }
        // Crear un objeto Resource para el archivo
        File file = new File(archivo.getRuta());
        Path filePath = Paths.get(archivo.getNombre());
        String extension = filePath.getFileName().toString();
        if (extension.lastIndexOf('.') != -1) {
            extension = extension.substring(extension.lastIndexOf('.') + 1);
        }
        // Determinar el tipo de contenido según la extensión del archivo
        String contentType;
        if ("pdf".equalsIgnoreCase(extension)) {
            contentType = "application/pdf";
        } else if ("xlsx".equalsIgnoreCase(extension)||"xls".equalsIgnoreCase(extension)) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if ("doc".equalsIgnoreCase(extension) || "docx".equalsIgnoreCase(extension)) {
            contentType = "application/msword";
        } else {
            // Tipo de archivo desconocido
            throw new RuntimeException();
        }
        if (!file.exists()) {
            throw new FileNotFoundException("El archivo no existe: " + archivo.getRuta());
        }

        try {
            // Leer el contenido del archivo en un arreglo de bytes
            byte[] fileContent = Files.readAllBytes(file.toPath());

            // Crear un objeto MultipartFile a partir del arreglo de bytes y el nombre del archivo
            MultipartFile multipartFile = new MockMultipartFile(archivo.getNombre(), archivo.getNombre(), contentType, fileContent);

            return multipartFile;
        } catch (IOException e) {
            throw new FileNotFoundException("Error al leer el archivo: " + archivo.getRuta());
        }
    }


}
