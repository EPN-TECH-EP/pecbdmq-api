package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionFinal;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionFinalDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionFinalRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProNotaProfesionalizacionFinalServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proNotaProfesionalizacionFinal")
public class ProNotaProfesionalizacionFinalResource extends ProfesionalizacionResource<ProNotaProfesionalizacionFinal, Integer, ProNotaProfesionalizacionFinalRepository, ProNotaProfesionalizacionFinalServiceImpl> {
    public ProNotaProfesionalizacionFinalResource(ProNotaProfesionalizacionFinalServiceImpl service){

        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProNotaProfesionalizacionFinal obj) throws DataException{
        return (ResponseEntity<ProNotaProfesionalizacionFinal>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodEstudianteSemestre(obj.getCodEstudianteSemestre());
            datosGuardados.setNotaParcial1(obj.getNotaParcial1());
            datosGuardados.setNotaParcial2(obj.getNotaParcial2());
            datosGuardados.setNotaPractica(obj.getNotaPractica());
            datosGuardados.setNotaAsistencia(obj.getNotaAsistencia());
            datosGuardados.setCodEstudiante(obj.getCodEstudiante());
            datosGuardados.setCodSemestre(obj.getCodSemestre());
            datosGuardados.setPromedioDisciplinaInstructor(obj.getPromedioDisciplinaInstructor());
            datosGuardados.setPromedioDisciplinaOficialSemana(obj.getPromedioDisciplinaOficialSemana());
            datosGuardados.setPromedioAcademico(obj.getPromedioAcademico());
            datosGuardados.setPromedioDisciplinaFinal(obj.getPromedioDisciplinaFinal());
            datosGuardados.setPonderacionAcademica(obj.getPonderacionAcademica());
            datosGuardados.setPonderacionDisciplina(obj.getPonderacionDisciplina());
            datosGuardados.setNotaFinalAcademica(obj.getNotaFinalAcademica());
            datosGuardados.setNotaFinalDisciplina(obj.getNotaFinalDisciplina());
            datosGuardados.setNotaFinal(obj.getNotaFinal());
            datosGuardados.setRealizoEncuesta(obj.getRealizoEncuesta());
            datosGuardados.setAprobado(obj.getAprobado());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/datos/{id}/{id2}/listar")
    public List<ProNotasProfesionalizacionFinalDto> findByMateriaParalelo(@PathVariable("id") Integer codPeriodo, @PathVariable("id2") Integer codSemestre){
        return service.findByMateriaParalelo(codPeriodo, codSemestre);
    }
}
