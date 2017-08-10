package br.unirio.dsw.selecaoppgi.model.edital;

import lombok.Getter;

/**
 * Enumeração dos possíveis status de um edital
 * 
 * @author marciobarros
 */
public enum StatusEdital
{
	Preparacao("PR"),
	Aberto("AB"),
	Homologacao("HM"),
	ProvaEscrita("PE"),
	ProvaAlinhamento("PA"),
	Finalizado("FN");
	
	private @Getter String codigo;
	
	private StatusEdital(String codigo)
	{
		this.codigo = codigo;
	}
	
	public static StatusEdital get(String codigo)
	{
		for (StatusEdital status : values())
			if (status.getCodigo().compareTo(codigo) == 0)
				return status;
		
		return null;
	}
}