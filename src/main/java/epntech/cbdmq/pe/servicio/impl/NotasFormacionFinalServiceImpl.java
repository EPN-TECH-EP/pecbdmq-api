package epntech.cbdmq.pe.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionFinalRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.NotasFormacionFinalService;

@Service
public class NotasFormacionFinalServiceImpl implements NotasFormacionFinalService {

	@Autowired
	private NotasFormacionFinalRepository notasFormacionFinalRepository;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	
	@Override
	public void cargarDisciplina(List<NotasFormacionFinal> lista) {
		int periodo = periodoAcademicoRepository.getPAActive();
		List<NotasFormacionFinal> nn = new ArrayList<>();
		NotasFormacionFinal notas = new NotasFormacionFinal();
		int i = 0;
		for (NotasFormacionFinal notasFormacionFinal : lista) {
			notas = lista.get(i);
			notas.setCodPeriodoAcademico(periodo);
			nn.add(notas);
			i++;
		}
		
		notasFormacionFinalRepository.saveAll(nn);
	}

	@Override
	public void calcularNotas() {
		notasFormacionFinalRepository.calcular_nota_final();
	}
	
	

}
