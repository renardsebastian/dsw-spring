package br.unirio.dsw.selecaoppgi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe que permite acesso aos dados de configuracao do sistema, que estao
 * armazenados em um arquivo .properties na raiz
 * 
 * @author marcio.barros
 */
public class Configuration 
{
	private static Properties configuracao = null;
	
	/**
	 * Carrega as configuracoes do arquivo
	 */
	private static void carregaConfiguracao()
	{
		configuracao = new Properties();
		
		try 
		{
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties");
			
			if (is != null)
				configuracao.load(is);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Retorna o endereco do servidor de e-mails
	 */
	public static String getSmtpGatewayHostname()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("EMAIL_HOSTNAME").trim(); 
	}

	/**
	 * Retorna o usuario do servidor de e-mails
	 */
	public static String getSmtpGatewayUser()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("SMTP_AUTH_USER").trim(); 
	}

	/**
	 * Retorna a senha do usuario no servidor de e-mails
	 */
	public static String getSmtpGatewayPassword()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("SMTP_AUTH_PWD").trim(); 
	}
	
	/**
	 * Retorna o endereco de origem dos e-mail
	 */
	public static String getEmailSource()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("EMAIL_SOURCE").trim(); 
	}

	/**
	 * Retorna o prefixo de notificacao de e-mail
	 */
	public static String getMailNotice()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("MAIL_NOTICE").trim(); 
	}
	
	/**
	 * Retorna o endereco do host
	 */
	public static String getHostname()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		String hostName = configuracao.getProperty("HOSTNAME").trim(); 
		String hostPath = configuracao.getProperty("HOSTPATH").trim();
		return hostName + hostPath;
	}

	/**
	 * Retorna o nome do ambiente
	 */
	public static String getEnvironmentName()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("ENVIRONMENT_NAME").trim(); 
	}
	
	/**
	 * Verifica se esta executando em ambiente de homologacao
	 */
	public static boolean isStaggingEnvironment()
	{
		return getEnvironmentName().trim().length() > 0; 
	}

	/**
	 * Retorna a connection string do banco de dados
	 */
	public static String getDatabaseConnectionString()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("CONNECTION_STRING"); 
	}

	/**
	 * Retorna o usuário para conexões com banco de dados
	 */
	public static String getDatabaseUser()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("CONNECTION_USER"); 
	}

	/**
	 * Retorna a senha do usuário para conexões com o banco de dados
	 */
	public static String getDatabasePassword()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("CONNECTION_PASSWORD"); 
	}
}