package epntech.cbdmq.pe.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import epntech.cbdmq.pe.dominio.UserPrincipal;
import epntech.cbdmq.pe.servicio.IntentoLoginService;

@Component
public class AuthenticationSuccessListener {
    private IntentoLoginService intentoLogin;

    @Autowired
    public AuthenticationSuccessListener(IntentoLoginService loginAttemptService) {
        this.intentoLogin = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
            intentoLogin.retirarUsuarioDeCache(user.getUsername());
        }
    }
}
