package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.selecaoppgi.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um projeto de pesquisa
 * 
 * @author Marcio Barros
 */
public class ProjetoPesquisa
{
	private @Getter @Setter String codigo;
	private @Getter @Setter String nome;
	private @Getter @Setter boolean exigeProvaOral;
	private List<User> professores;
	private List<ProvaEscrita> provasEscritas;
	
	/**
	 * Inicializa um projeto de pesquisa em um edital
	 */
	public ProjetoPesquisa()
	{
		this.codigo = "";
		this.nome = "";
		this.exigeProvaOral = false;
		this.professores = new ArrayList<User>();
		this.provasEscritas = new ArrayList<ProvaEscrita>();
	}

	public Iterable<User> getProfessores()
	{
		return professores;
	}

	public void adicionaProfessor(User professor)
	{
		this.professores.add(professor);
	}

	public Iterable<ProvaEscrita> getProvasEscritas()
	{
		return provasEscritas;
	}

	public void adicionaProvaEscrita(ProvaEscrita prova)
	{
		this.provasEscritas.add(prova);
	}
}