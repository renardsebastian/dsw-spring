package br.unirio.dsw.crud.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

/**
 * Classe que representa um usuário do sistema
 * 
 * @author marciobarros
 */
public class User extends SocialUser
{
	private static final long serialVersionUID = 7512107428170018274L;

	private @Setter @Getter long id;
	private @Setter @Getter String name;
	private @Setter @Getter String email;
	private @Setter @Getter String password;
	private @Setter @Getter Role role;
	private @Setter @Getter SignInProvider provider;

	/**
	 * Inicializa um usuário
	 */
	public User()
	{
		this(-1, "SocialUser", "no@email.com", "XXXXXX");
	}

	/**
	 * Inicializa um usuário
	 */
	public User(long id, String name, String email, String password)
	{
        super(email, password, createAuthoritiesFromRole());
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = Role.BASIC;
		this.provider = null;
	}

	private static Set<GrantedAuthority> createAuthoritiesFromRole()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.BASIC.toString());
        authorities.add(authority);
		return authorities;
	}

	/**
	 * Apresenta os dados do usuário em uma string
	 */
	@Override
	public String toString()
	{
		return "User [id=" + id + ", email=" + email + "]";
	}
	
	/**
	 * Papéis que podem ser assumidos pelos usuários
	 * 
	 * @author marciobarros
	 */
	public enum Role 
	{
	    BASIC,
	    OFFICE,
	    PROFESSOR,
	    ROOT
	}
	
	/**
	 * Provedor de serviço de sign-in
	 * 
	 * @author marciobarros
	 */
	public enum SignInProvider 
	{
	    FACEBOOK,
	    TWITTER
	}
}