
package epntech.cbdmq.pe.servicio;

import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import jakarta.transaction.Transactional;

public interface ConvocatoriaForService {	

    @Transactional
    void insertarConvocatoriaConDocumentos(Integer periodo, Integer modulo,  String nombre, String estado, Date fechaInicio, Date fechaFin, LocalTime horaInicio, LocalTime horaFin, String codigoUnico, Integer cupoHombres, Integer cupoMujeres, Set<DocumentoFor> documentos);
}
