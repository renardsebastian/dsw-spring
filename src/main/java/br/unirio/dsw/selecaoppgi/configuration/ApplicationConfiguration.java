package br.unirio.dsw.selecaoppgi.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe que permite acesso aos dados de configuracao do sistema, que estao
 * armazenados em um arquivo .properties na raiz
 * 
 * @author marcio.barros
 */
public class ApplicationConfiguration 
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
	 * Retorna o endereco do host
	 */
	public static String getHostname()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("HOSTNAME").trim(); 
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
}