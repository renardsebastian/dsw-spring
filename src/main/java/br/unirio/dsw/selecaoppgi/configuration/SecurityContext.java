package br.unirio.dsw.selecaoppgi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;

/**
 * Classe responsável pela configuração de segurança do Spring MVC
 * 
 * @author marciobarros
 */
@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter
{
	/**
	 * Indica os caminhos que serão ignorados pelos controle de segurança (arquivos CSS e JS)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/static/**");
	}

	/**
	 * Configura o processo de autenticação
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		PasswordEncoder encoder = passwordEncoder();
		
		UserDetailsService userDetailService = userDetailsService();
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
		
		AuthenticationService authenticationService = new AuthenticationService();
		authenticationService.setPasswordEncoder(encoder);
		auth.authenticationProvider(authenticationService);
	}

	/**
	 * Configura o acesso às diversas partes da aplicação
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// Configures form login
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login/authenticate")
			.failureUrl("/login?error")
			
			// Configures the logout function
			.and()
			.logout()
			.deleteCookies("JSESSIONID")
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")
			
			// Configures url based authorization
			.and()
			.authorizeRequests()
			.antMatchers("/auth/**", "/login/**").permitAll()
			.antMatchers("/**").hasRole("BASIC")
			
			// Adds the filter to Spring Security's filter chain.
			.and().apply(new SpringSocialConfigurer());
	}

	/**
	 * Retorna o objeto que codifica senhas na base local
	 */
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}

	/**
	 * Retorna o objeto que permite acesso aos dados dos usuários na base local
	 */
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new LocalAccountUserDetailsService();
	}

	/**
	 * Retorna o objeto que permite acesso aos dados dos usuários nas redes sociais
	 */
	@Bean
	public SocialUserDetailsService socialUserDetailsService()
	{
		return new SocialNetworkUserDetailsService();
	}

	/**
	 * Classe de acesso aos dados dos usuários em redes sociais para o Spring Security
	 * 
	 * @author marciobarros
	 */
	private class SocialNetworkUserDetailsService implements SocialUserDetailsService 
	{
		@Autowired
		private UserDAO userDAO;
		
	    @Override
	    public SocialUserDetails loadUserByUserId(String username) throws UsernameNotFoundException, DataAccessException 
	    {
	        return (SocialUserDetails) userDAO.getUserEmail(username);
	    }
	}
	
	/**
	 * Classe de acesso aos dados dos usuários na base local para o Spring Security
	 * 
	 * @author marciobarros
	 */
	private class LocalAccountUserDetailsService implements UserDetailsService 
	{
		@Autowired
		private UserDAO userDAO;
		
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	    {
	        return userDAO.getUserEmail(username);
	    }
	}
	
	/**
	 * Classe responsável pela autenticação de usuários
	 * 
	 * @author marciobarros
	 */
	private class AuthenticationService extends DaoAuthenticationProvider
	{
		@Autowired
		private UserDAO userDAO;
	
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException
		{
			try
			{
				Authentication auth = super.authenticate(authentication);
				userDAO.registerSuccessfulLogin(authentication.getName());
				return auth;
			} 
			catch (BadCredentialsException e)
			{
				userDAO.registerFailedLogin(authentication.getName());
				throw e;
			} 
			catch (LockedException e)
			{
				throw e;
			}
		}
	}
}