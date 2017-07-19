package br.unirio.dsw.crud.view.login;

import lombok.Data;
import br.unirio.dsw.crud.model.SignInProvider;

/**
 * Classe do formulário de registro de um novo usuário
 * 
 * @author marciobarros
 */
public @Data class RegistrationForm
{
	private long id = -1;
	private String name = "";
	private String email = "";
	private String password = "";
	private String repeatPassword = "";
	private SignInProvider provider = null;
}