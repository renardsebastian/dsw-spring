package br.unirio.dsw.crud.dao;

/**
 * Classe que concentra o acesso aos DAOs do sistema
 * 
 * @author marciobarros
 */
public class DAOFactory
{
	private static UserDAO usuarioDAO;

	/**
	 * Retorna a instancia do DAO para persistencia dos usuarios
	 */
	public static UserDAO getUserDAO()
	{
		if (usuarioDAO == null)
			usuarioDAO = new UserDAO();
		
		return usuarioDAO;
	}
}