package epntech.cbdmq.pe.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import epntech.cbdmq.pe.servicio.IntentoLoginService;

@Component
public class AutenticacionFalloListener {
    private IntentoLoginService loginAttemptService;

    @Autowired
    public AutenticacionFalloListener(IntentoLoginService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.agregarUsuarioACache(username);
        }

    }
}
