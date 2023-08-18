package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Optional;

public interface ProConvocatoriaService extends ProfesionalizacionService<ProConvocatoria, Integer> {

    Optional<ProConvocatoria> getByCodigoUnicoConvocatoria (String CodigoUnicoConvocatoria);
    public String getCodConvocatoriaCreacion();
    public String getEstado();
    void notificar(Integer codConvocatoria) throws MessagingException, DataException, IOException;

}
