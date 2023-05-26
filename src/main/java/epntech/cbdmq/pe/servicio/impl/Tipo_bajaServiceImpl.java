package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dto.Tipo_BajaDTO;

import epntech.cbdmq.pe.dominio.util.Tipo_baja;

import epntech.cbdmq.pe.repositorio.admin.Tipo_bajaRepository;
import epntech.cbdmq.pe.servicio.Tipo_bajaService;

@Service
public class Tipo_bajaServiceImpl implements Tipo_bajaService {

	
	@Autowired
	private Tipo_bajaRepository repo;
	@Override
	public List<Tipo_BajaDTO> findByBaja(String estado) {
		List<Tipo_baja> lista=this.repo.findByEstado(estado);
		return lista.stream().map((bean)->convertirBeanDto(bean))
				.collect(Collectors.toList());
	}
	
	public Tipo_BajaDTO convertirBeanDto(Tipo_baja bajas) {
		return Tipo_BajaDTO.builder()
				.codigo(bajas.getCod_tipo_baja())
				.baja(bajas.getBaja())
				.estado(bajas.getEstado())
				.build();
	}
	

	
	
	
	
	
	
}
