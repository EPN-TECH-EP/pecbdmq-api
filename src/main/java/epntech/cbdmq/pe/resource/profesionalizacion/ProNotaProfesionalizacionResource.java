package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacion;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProNotaProfesionalizacionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proNotaProfesionalizacion")
public class ProNotaProfesionalizacionResource extends ProfesionalizacionResource<ProNotaProfesionalizacion, Integer, ProNotaProfesionalizacionRepository, ProNotaProfesionalizacionServiceImpl>{
    public ProNotaProfesionalizacionResource(ProNotaProfesionalizacionServiceImpl service){
        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProNotaProfesionalizacion obj) throws DataException{
        return service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodEstudianteSemestreMateriaParalelo(obj.getCodEstudianteSemestreMateriaParalelo());
            datosGuardados.setNotaParcial1(obj.getNotaParcial1());
            datosGuardados.setNotaParcial2(obj.getNotaParcial2());
            datosGuardados.setNotaPractica(obj.getNotaPractica());
            datosGuardados.setNotaAsistencia(obj.getNotaAsistencia());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setCodInstructor(obj.getCodInstructor());
            datosGuardados.setCodMateria(obj.getCodMateria());
            datosGuardados.setCodEstudiante(obj.getCodEstudiante());
            datosGuardados.setCodSemestre(obj.getCodSemestre());
            datosGuardados.setNotaMinima(obj.getNotaMinima());
            datosGuardados.setPesoMateria(obj.getPesoMateria());
            datosGuardados.setNumeroHoras(obj.getNumeroHoras());
            datosGuardados.setNotaMateria(obj.getNotaMateria());
            datosGuardados.setNotaPonderacion(obj.getNotaPonderacion());
            datosGuardados.setNotaDisciplina(obj.getNotaDisciplina());
            datosGuardados.setNotaSupletorio(obj.getNotaSupletorio());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/datos/{id}/listar")
    public List<ProNotasProfesionalizacionDto> findByMateriaParalelo(@PathVariable("id") Integer codMateriaParalelo){
        return service.findByMateriaParalelo(codMateriaParalelo);
    }

    @GetMapping("/filtrar/{semestre}/{periodo}")
    public List<ProNotasProfesionalizacionDto> getByAll(@PathVariable("semestre") Integer codSemestre, @PathVariable("periodo") Integer codPeriodo) {
        if (codSemestre > 0 && codPeriodo > 0) {
            return service.getByAll(codSemestre, codPeriodo);
        } else if (codSemestre > 0) {
            return service.getByAll(codSemestre);
        } else if (codPeriodo > 0) {
            return service.getByAllPeriodo(codPeriodo);
        }
        return service.getByAll();
    }
}
