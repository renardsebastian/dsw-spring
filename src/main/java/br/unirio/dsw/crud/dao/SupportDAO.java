package br.unirio.dsw.crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.unirio.dsw.crud.utils.Configuration;

/**
 * Classe de suporte a persistencia de informacoes
 * 
 * @author marcio.barros
 */
public class SupportDAO
{
	/**
	 * Cria uma conexao com o banco de dados
	 */
	public static Connection getConnection()
	{	
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionString = Configuration.getDatabaseConnectionString();
			String connectionUser = Configuration.getDatabaseUser();
			String connectionPassword = Configuration.getDatabasePassword();
			Connection conexao = DriverManager.getConnection(connectionString, connectionUser, connectionPassword);
			return conexao;
			
		} catch (SQLException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - erro de SQL");
			System.out.println(e.getMessage());
			return null;
		} catch (ClassNotFoundException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - driver nao encontrado");
			return null;
		} catch (InstantiationException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - erro de instanciacao do driver");
			return null;
		} catch (IllegalAccessException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - acesso ilegal no driver");
			return null;
		}
	}
	
	/**
	 * Apresenta uma mensagem no log do sistema
	 */
	public static void log(String mensagem)
	{
		System.out.println(mensagem);
	}
}