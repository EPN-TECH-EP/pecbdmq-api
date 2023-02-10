package epntech.cbdmq.pe.dominio;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2566624400445006252L;
	private Usuario usuario;

	public UserPrincipal(Usuario user) {
		this.usuario = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return stream(this.usuario.getPermisos()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return this.usuario.getClave();
	}

	@Override
	public String getUsername() {
		return this.usuario.getNombreUsuario();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.usuario.isNotLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.usuario.isActive();
	}
}
