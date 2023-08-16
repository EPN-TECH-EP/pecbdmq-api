package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloEstudiante;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloEstudianteDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.UsuarioDatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProParaleloEstudianteRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProParaleloEstudianteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proSemestreMateriaParaleloEstudiante")
public class ProParaleloEstudianteResource extends ProfesionalizacionResource<ProSemestreMateriaParaleloEstudiante, Integer, ProParaleloEstudianteRepository, ProParaleloEstudianteServiceImpl> {

    private final UsuarioDatoPersonalRepository usuarioDatoPersonalRepository;
    public ProParaleloEstudianteResource(ProParaleloEstudianteServiceImpl service, UsuarioDatoPersonalRepository usuarioDatoPersonalRepository) {
        super(service);
        this.usuarioDatoPersonalRepository = usuarioDatoPersonalRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProSemestreMateriaParaleloEstudiante obj) throws DataException {
        return (ResponseEntity<ProSemestreMateriaParaleloEstudiante>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodEstudiante(obj.getCodEstudiante());
            datosGuardados.setCodSemestreMateriaParalelo(obj.getCodSemestreMateriaParalelo());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/listar")
    public List<ProParaleloEstudianteDto> findByMateriaParalelo (@PathVariable("id") Integer codMateriaParalelo){
        return service.findByMateriaParalelo(codMateriaParalelo);
    }
}
