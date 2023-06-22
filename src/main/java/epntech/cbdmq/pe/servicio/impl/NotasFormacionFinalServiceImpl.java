package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.NO_PERIODO_ACTIVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
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
	public void cargarDisciplina(List<NotasFormacionFinal> lista) throws DataException {
		Integer periodo = periodoAcademicoRepository.getPAActive();
		if(periodo == null)
			throw new DataException(NO_PERIODO_ACTIVO);
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
	
	@Override
	public void cambiaEstadoRealizoEncuesta(Long codEstudiante) {
		// TODO Auto-generated method stub
		notasFormacionFinalRepository.actualizarEstadorealizoEncuesta(codEstudiante);
	}
	
	@Override
	public Boolean realizoEncuesta(Long codEstudiante) {
		// TODO Auto-generated method stub
		return notasFormacionFinalRepository.realizoEncuesta(codEstudiante);
	}

	@Override
	public Optional<NotasFormacionFinal> getByEstudiante(Long codEstudiante) {
		// TODO Auto-generated method stub
		return notasFormacionFinalRepository.getByEstudiante(codEstudiante);
	}

}

