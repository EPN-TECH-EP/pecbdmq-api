package epntech.cbdmq.pe.configuracion;

import static epntech.cbdmq.pe.constante.SeguridadConst.URLS_PUBLICAS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import epntech.cbdmq.pe.filtro.JwtAccesoDenegadoHandler;
import epntech.cbdmq.pe.filtro.JwtAutenticacionEntryPoint;
import epntech.cbdmq.pe.filtro.JwtFiltroAutorizacionFilter;
import epntech.cbdmq.pe.servicio.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SeguridadConfig {
	
	private JwtFiltroAutorizacionFilter jwtAuthorizationFilter;
	private JwtAccesoDenegadoHandler jwtAccessDeniedHandler;
	private JwtAutenticacionEntryPoint jwtAuthenticationEntryPoint;
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
    public SeguridadConfig(JwtFiltroAutorizacionFilter jwtAuthorizationFilter,
                                 JwtAccesoDenegadoHandler jwtAccessDeniedHandler,
                                 JwtAutenticacionEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("userDetailsService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioService userDetailService) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}


	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
	    
		http.csrf().disable().cors().and()
        .sessionManagement().sessionCreationPolicy(STATELESS)
        .and().authorizeHttpRequests().requestMatchers(URLS_PUBLICAS).permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		
		/*http.csrf()
	      .disable()
	      .authorizeRequests()
	      .antMatchers(HttpMethod.DELETE)
	      .hasRole("ADMIN")
	      .antMatchers("/admin/**")
	      .hasAnyRole("ADMIN")
	      .antMatchers("/user/**")
	      .hasAnyRole("USER", "ADMIN")
	      .antMatchers("/login/**")
	      .anonymous()
	      .anyRequest()
	      .authenticated()
	      .and()
	      .httpBasic()
	      .and()
	      .sessionManagement()
	      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

	    return http.build();
	}

	
}


/*
public class SeguridadConfig extends WebSecurityConfigurerAdapter  {

	private JwtFiltroAutorizacionFilter jwtAuthorizationFilter;
	private JwtAccesoDenegadoHandler jwtAccessDeniedHandler;
	private JwtAutenticacionEntryPoint jwtAuthenticationEntryPoint;
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
    public SeguridadConfig(JwtFiltroAutorizacionFilter jwtAuthorizationFilter,
                                 JwtAccesoDenegadoHandler jwtAccessDeniedHandler,
                                 JwtAutenticacionEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("userDetailsService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests().antMatchers(URLS_PUBLICAS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	

}
*/