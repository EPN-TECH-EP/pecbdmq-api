
package epntech.cbdmq.pe.servicio.impl;

import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaForRepository;
import epntech.cbdmq.pe.servicio.ConvocatoriaForService;

@Service
public class ConvocatoriaForServiceImpl implements ConvocatoriaForService {

	@Autowired
	private ConvocatoriaForRepository repo;

	@Override
	public void insertarConvocatoriaConDocumentos(Integer periodo, Integer modulo, String nombre, String estado,
			Date fechaInicio, Date fechaFin, LocalTime horaInicio, LocalTime horaFin, String codigoUnico,
			Integer cupoHombres, Integer cupoMujeres, Set<DocumentoFor> documentos) {

		repo.insertarConvocatoriaConDocumentos(periodo, modulo, nombre, estado, fechaInicio, fechaFin, horaInicio,
				horaFin, codigoUnico, cupoHombres, cupoMujeres, documentos);

	}

}

