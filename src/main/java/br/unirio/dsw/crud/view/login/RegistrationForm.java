package br.unirio.dsw.crud.view.login;

import lombok.Data;

/**
 * Classe do formulário de registro de um novo usuário
 * 
 * @author marciobarros
 */
public @Data class RegistrationForm
{
	private String name = "";
	private String email = "";
	private String password = "";
	private String repeatPassword = "";
}