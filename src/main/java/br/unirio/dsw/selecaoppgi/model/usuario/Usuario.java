package br.unirio.dsw.selecaoppgi.model.usuario;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um usuário do sistema
 * 
 * @author marciobarros
 */
public class Usuario extends org.springframework.security.core.userdetails.User
{
	private static final long serialVersionUID = 7512107428170018274L;

	private @Setter @Getter int id;
	private @Setter @Getter String nome;
	private @Setter @Getter PapelUsuario papel;
	private @Setter @Getter DateTime dataUltimoLogin;
	private @Setter @Getter int contadorLoginFalhas;
	private @Setter @Getter String tokenLogin;
	private @Setter @Getter DateTime dataTokenLogin;

	/**
	 * Inicializa um usuário
	 */
	public Usuario(String name, String email, String password, boolean locked)
	{
        super(email, password, true, true, true, !locked, createAuthoritiesFromBasicRole());
		this.id = -1;
		this.nome = name;
		this.papel = PapelUsuario.ROLE_BASIC;
	}

	/**
	 * Cria os direitos de acesso relacionado ao papel básico do usuário
	 */
	private static Set<GrantedAuthority> createAuthoritiesFromBasicRole()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(PapelUsuario.ROLE_BASIC.toString());
        authorities.add(authority);
		return authorities;
	}
}