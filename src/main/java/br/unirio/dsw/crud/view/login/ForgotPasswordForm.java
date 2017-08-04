package br.unirio.dsw.crud.view.login;

import lombok.Data;

/**
 * Classe do formulário de esquecimento de senha
 * 
 * @author marciobarros
 */
public @Data class ForgotPasswordForm
{
	private String email = "";
}