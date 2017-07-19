package br.unirio.dsw.crud.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
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

	private @Setter @Getter int id;
	private @Setter @Getter String name;
	private @Setter @Getter Role role;
	private @Setter @Getter String socialId;
	private @Setter @Getter SignInProvider provider;
	private @Setter @Getter boolean mustResetPassword;
	private @Setter @Getter DateTime lastLoginDate;
	private @Setter @Getter int failedLoginCounter;
	private @Setter @Getter String loginToken;
	private @Setter @Getter DateTime loginTokenDate;

	/**
	 * Inicializa um usuário
	 */
	public User(String name, String email, String password)
	{
        super(email, password, createAuthoritiesFromRole());
		this.id = -1;
		this.name = name;
		this.role = Role.ROLE_BASIC;
		this.provider = null;
	}

	private static Set<GrantedAuthority> createAuthoritiesFromRole()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.ROLE_BASIC.toString());
        authorities.add(authority);
		return authorities;
	}

	/**
	 * Apresenta os dados do usuário em uma string
	 */
	@Override
	public String toString()
	{
		return "User [id=" + id + ", email=" + getUsername() + "]";
	}
}