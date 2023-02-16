package epntech.cbdmq.pe.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import epntech.cbdmq.pe.servicio.IntentoLoginService;

@Component
public class AuthenticationFailureListener {
    private IntentoLoginService intentoLogin;

    @Autowired
    public AuthenticationFailureListener(IntentoLoginService loginAttemptService) {
        this.intentoLogin = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            intentoLogin.agregarUsuarioACache(username);
        }

    }
}
