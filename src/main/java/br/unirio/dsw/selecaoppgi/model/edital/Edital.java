package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.unirio.dsw.selecaoppgi.model.usuario.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um edital
 * 
 * @author Marcio Barros
 */
public class Edital
{
	private @Getter @Setter int id;
	private @Getter @Setter String nome;
	private @Getter @Setter DateTime dataInicio;
	private @Getter @Setter DateTime dataTermino;
	private @Getter @Setter StatusEdital status;
	private @Getter @Setter int notaMinimaAlinhamento;
	private List<User> comissaoSelecao;
	private List<User> comissaoRecurso;
	private List<ProvaEscrita> provasEscritas;
	private List<ProjetoPesquisa> projetosPesquisa;
	private List<CriterioAlinhamento> criteriosAlinhamento;

	/**
	 * Inicializa o edital
	 */
	public Edital()
	{
		this.id = -1;
		this.nome = "";
		this.dataInicio = null;
		this.dataTermino = null;
		this.status = StatusEdital.Preparacao;
		this.notaMinimaAlinhamento = 70;
		this.comissaoSelecao = new ArrayList<User>();
		this.comissaoRecurso = new ArrayList<User>();
		this.provasEscritas = new ArrayList<ProvaEscrita>();
		this.projetosPesquisa = new ArrayList<ProjetoPesquisa>();
		this.criteriosAlinhamento = new ArrayList<CriterioAlinhamento>();
	}
	
	/**
	 * Retorna uma prova escrita, dado seu código
	 */
	public ProvaEscrita pegaProvaEscritaCodigo(String codigo)
	{
		for (ProvaEscrita prova : provasEscritas)
			if (prova.getCodigo().compareToIgnoreCase(codigo) == 0)
				return prova;
		
		return null;
	}

	/**
	 * Retorna a composição da comissão de seleção
	 */
	public Iterable<User> getComissaoSelecao()
	{
		return comissaoSelecao;
	}

	/**
	 * Adiciona um membro na comissão de seleção
	 */
	public void adicionaComissaoSelecao(User professor)
	{
		this.comissaoSelecao.add(professor);
	}

	/**
	 * Retorna a composição da comissão de recursos
	 */
	public Iterable<User> getComissaoRecursos()
	{
		return comissaoRecurso;
	}

	/**
	 * Adiciona um membro na comissão de recursos
	 */
	public void adicionaComissaoRecurso(User professor)
	{
		this.comissaoRecurso.add(professor);
	}

	/**
	 * Retorna a lista de provas escritas
	 */
	public Iterable<ProvaEscrita> getProvasEscritas()
	{
		return provasEscritas;
	}

	/**
	 * Adiciona uma prova escrita no edital
	 */
	public void adicionaProvasEscrita(ProvaEscrita prova)
	{
		this.provasEscritas.add(prova);
	}

	/**
	 * Retorna a lista de projetos de pesquisa
	 */
	public Iterable<ProjetoPesquisa> getProjetosPesquisa()
	{
		return projetosPesquisa;
	}

	/**
	 * Retorna um projeto de pesquisa, dado seu código
	 */
	public ProjetoPesquisa pegaProjetoPesquisaCodigo(String codigo)
	{
		for (ProjetoPesquisa projeto : projetosPesquisa)
			if (projeto.getCodigo().compareToIgnoreCase(codigo) == 0)
				return projeto;
		
		return null;
	}

	/**
	 * Adiciona um projeto de pesquisa no edital
	 */
	public void adicionaProjetoPesquisa(ProjetoPesquisa projeto)
	{
		this.projetosPesquisa.add(projeto);
	}

	/**
	 * Retorna a lista de critérios de alinhamento
	 */
	public Iterable<CriterioAlinhamento> getCriteriosAlinhamento()
	{
		return criteriosAlinhamento;
	}

	/**
	 * Adiciona um novo critério de alinhamento
	 */
	public void adicionaCriterioAlinhamento(CriterioAlinhamento criterio)
	{
		this.criteriosAlinhamento.add(criterio);
	}
}