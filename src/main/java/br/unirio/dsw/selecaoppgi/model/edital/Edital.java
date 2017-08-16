package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.User;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;
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
	private List<User> comissaoRecursos;
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
		this.comissaoRecursos = new ArrayList<User>();
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

	/**
	 * Carrega a partir da representação JSON
	 */
	public void fromJson(JsonObject json, UserDAO userDAO)
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

		String sDataInicio = json.get("dataInicio").getAsString();
		this.dataInicio = fmt.parseDateTime(sDataInicio);

		String sDataTermino = json.get("dataTermino").getAsString();
		this.dataTermino = fmt.parseDateTime(sDataTermino);
		
		this.notaMinimaAlinhamento = json.get("notaMinimaAlinhamento").getAsInt();

		carregaRepresentacaoComissaoSelecao(json, userDAO);
		carregaRepresentacaoComissaoRecursos(json, userDAO);
		carregaRepresentacaoProvasEscritas(json);
		carregaRepresentacaoProjetosPesquisa(json, userDAO);
		carregaRepresentacaoCriteriosAlinhamento(json);
	}

	/**
	 * Carrega a comissão de seleção a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoSelecao(JsonObject json, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("selecao");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				comissaoSelecao.add(professor);
		}
	}

	/**
	 * Carrega a comissão de recursos a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoRecursos(JsonObject json, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("recursos");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				comissaoSelecao.add(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritas(JsonObject json)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			JsonObject jsonProva = jsonProvas.get(i).getAsJsonObject();
			ProvaEscrita prova = new ProvaEscrita();
			prova.fromJson(jsonProva);
			provasEscritas.add(prova);
		}
	}

	/**
	 * Carrega a lista de projetos de pesquisa a partir da representação JSON
	 */
	private void carregaRepresentacaoProjetosPesquisa(JsonObject json, UserDAO userDAO)
	{
		JsonArray jsonProjetos = json.getAsJsonArray("projetos");
		
		for (int i = 0; i < jsonProjetos.size(); i++)
		{
			JsonObject jsonProjeto = jsonProjetos.get(i).getAsJsonObject();
			ProjetoPesquisa projeto = new ProjetoPesquisa();
			projeto.fromJson(jsonProjeto, this, userDAO);
			projetosPesquisa.add(projeto);
		}
	}

	/**
	 * Carrega a lista de critérios de alinhamento a partir da representação JSON
	 */
	private void carregaRepresentacaoCriteriosAlinhamento(JsonObject json)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criterios");
		
		for (int i = 0; i < jsonCriterios.size(); i++)
		{
			JsonObject jsonCriterio = jsonCriterios.get(i).getAsJsonObject();
			CriterioAlinhamento criterio = new CriterioAlinhamento();
			criterio.fromJson(jsonCriterio);
			criteriosAlinhamento.add(criterio);
		}
	}

	public Iterable<User> getComissaoSelecao()
	{
		return comissaoSelecao;
	}

	public Iterable<User> getComissaoRecursos()
	{
		return comissaoRecursos;
	}

	public Iterable<ProvaEscrita> getProvasEscritas()
	{
		return provasEscritas;
	}

	public Iterable<ProjetoPesquisa> getProjetosPesquisa()
	{
		return projetosPesquisa;
	}

	public Iterable<CriterioAlinhamento> getCriteriosAlinhamento()
	{
		return criteriosAlinhamento;
	}
}