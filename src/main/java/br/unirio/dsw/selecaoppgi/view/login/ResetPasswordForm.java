package br.unirio.dsw.selecaoppgi.view.login;

import lombok.Data;

/**
 * Classe do formulário de reset de senha
 * 
 * @author marciobarros
 */
public @Data class ResetPasswordForm 
{
	private String email;
	private String token;
	private String newPassword;
	private String repeatNewPassword;
}
