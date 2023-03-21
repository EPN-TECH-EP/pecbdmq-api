package epntech.cbdmq.pe.servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Excel;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.ExcelRepository;

@Service
public class ExcelService {
  @Autowired
  ExcelRepository repository;

  public void save(MultipartFile file) {
    try {
      List<Excel> datos = ExcelHelper.excelToDatos(file.getInputStream());
     
      repository.saveAll(datos);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Excel> datos = repository.findAll();

    ByteArrayInputStream in = ExcelHelper.datosToExcel(datos);
    return in;
  }

  public List<Excel> getAllDatos() {
    return repository.findAll();
  }
}
