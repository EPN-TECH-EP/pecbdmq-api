package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.NotificacionPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.admin.NotificacionPruebaRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.NotificacionPruebaService;
import jakarta.mail.MessagingException;

@Service
public class NotificacionPruebaServiceImpl implements NotificacionPruebaService{

	@Autowired
	NotificacionPruebaRepository repo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	DatoPersonalRepository repo2;
	
	/*@Autowired
	private */
	
	
	
	@Override
	public NotificacionPrueba save(NotificacionPrueba obj) throws DataException, MessagingException {
		if( obj.getFecha_prueba()==null|| obj.getMensaje().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		
		emailService.notificacionEmail(obj.getFecha_prueba(), obj.getMensaje(), repo2.findById(obj.getCod_datos_personales()).get().getCorreo_personal());
        return repo.save(obj);
	}

	@Override
	public List<NotificacionPrueba> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	

	
	
	
	
	
}
