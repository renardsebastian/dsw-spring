package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.unirio.dsw.selecaoppgi.model.User;
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
	 * Retorna uma prova escrita, dada sua sigla
	 */
	public ProvaEscrita pegaProvaEscritaSigla(String sigla)
	{
		for (ProvaEscrita prova : provasEscritas)
			if (prova.getSigla().compareToIgnoreCase(sigla) == 0)
				return prova;
		
		return null;
	}

	public Iterable<User> getComissaoSelecao()
	{
		return comissaoSelecao;
	}

	public void adicionaComissaoSelecao(User professor)
	{
		this.comissaoSelecao.add(professor);
	}

	public Iterable<User> getComissaoRecursos()
	{
		return comissaoRecurso;
	}

	public void adicionaComissaoRecurso(User professor)
	{
		this.comissaoRecurso.add(professor);
	}

	public Iterable<ProvaEscrita> getProvasEscritas()
	{
		return provasEscritas;
	}

	public void adicionaProvasEscrita(ProvaEscrita prova)
	{
		this.provasEscritas.add(prova);
	}

	public Iterable<ProjetoPesquisa> getProjetosPesquisa()
	{
		return projetosPesquisa;
	}

	public void adicionaProjetoPesquisa(ProjetoPesquisa projeto)
	{
		this.projetosPesquisa.add(projeto);
	}

	public Iterable<CriterioAlinhamento> getCriteriosAlinhamento()
	{
		return criteriosAlinhamento;
	}

	public void adicionaCriterioAlinhamento(CriterioAlinhamento criterio)
	{
		this.criteriosAlinhamento.add(criterio);
	}
}