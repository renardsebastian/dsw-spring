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
	 * Gera a representação JSON
	 */
	public JsonObject toJson()
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		JsonObject json = new JsonObject();
		json.addProperty("notaMinimaAlinhamento", notaMinimaAlinhamento);
		json.addProperty("dataInicio", fmt.print(dataInicio));
		json.addProperty("dataTermino", fmt.print(dataTermino));
		json.add("selecao", geraRepresentacaoComissaoSelecao());
		json.add("recursos", geraRepresentacaoComissaoRecursos());
		json.add("provas", geraRepresentacaoProvasEscritas());
		json.add("projetos", geraRepresentacaoProjetosPesquisa());
		json.add("criterios", geraRepresentacaoCriteriosAlinhamento());
		return json;
	}

	/**
	 * Gera a representação JSON da comissão de seleção
	 */
	private JsonArray geraRepresentacaoComissaoSelecao()
	{
		JsonArray jsonComissao = new JsonArray();
		
		for (User professor : comissaoSelecao)
		{
			JsonObject jsonProfessor = new JsonObject();
			jsonProfessor.addProperty("id", professor.getId());
			jsonProfessor.addProperty("nome", professor.getName());
			jsonComissao.add(jsonProfessor);
		}
		
		return jsonComissao;
	}

	/**
	 * Gera a representação JSON da comissão de recursos
	 */
	private JsonArray geraRepresentacaoComissaoRecursos()
	{
		JsonArray jsonComissao = new JsonArray();
		
		for (User professor : comissaoRecursos)
		{
			JsonObject jsonProfessor = new JsonObject();
			jsonProfessor.addProperty("id", professor.getId());
			jsonProfessor.addProperty("nome", professor.getName());
			jsonComissao.add(jsonProfessor);
		}
		
		return jsonComissao;
	}

	/**
	 * Gera a representação JSON da lista de provas escritas
	 */
	private JsonArray geraRepresentacaoProvasEscritas()
	{
		JsonArray jsonProvas = new JsonArray();
		
		for (ProvaEscrita prova : provasEscritas)
			jsonProvas.add(prova.toJson());
		
		return jsonProvas;
	}

	/**
	 * Gera a representação JSON da lista de projetos de pesquisa
	 */
	private JsonArray geraRepresentacaoProjetosPesquisa()
	{
		JsonArray jsonProjetos = new JsonArray();
		
		for (ProjetoPesquisa projeto : projetosPesquisa)
			jsonProjetos.add(projeto.toJson());
		
		return jsonProjetos;
	}

	/**
	 * Gera a representação JSON da lista de projetos de pesquisa
	 */
	private JsonArray geraRepresentacaoCriteriosAlinhamento()
	{
		JsonArray jsonCriterios = new JsonArray();
		
		for (CriterioAlinhamento criterio : criteriosAlinhamento)
			jsonCriterios.add(criterio.toJson());
		
		return jsonCriterios;
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
		// TODO terminar
		
//		JsonArray jsonProvas = json.getAsJsonArray("provas");
//		
//		for (int i = 0; i < jsonProvas.size(); i++)
//		{
//			String sigla = jsonProvas.get(i).getAsString();
//			ProvaEscrita prova = edital.pegaProvaEscritaSigla(sigla);
//			
//			if (prova != null)
//				provasEscritas.add(prova);
//		}
	}

	/**
	 * Carrega a lista de projetos de pesquisa a partir da representação JSON
	 */
	private void carregaRepresentacaoProjetosPesquisa(JsonObject json, UserDAO userDAO)
	{
		// TODO terminar
		
//		JsonArray jsonProvas = json.getAsJsonArray("projetos");
//		
//		for (int i = 0; i < jsonProvas.size(); i++)
//		{
//			String sigla = jsonProvas.get(i).getAsString();
//			ProvaEscrita prova = edital.pegaProvaEscritaSigla(sigla);
//			
//			if (prova != null)
//				provasEscritas.add(prova);
//		}
	}

	/**
	 * Carrega a lista de critérios de alinhamento a partir da representação JSON
	 */
	private void carregaRepresentacaoCriteriosAlinhamento(JsonObject json)
	{
		// TODO terminar
		
//		JsonArray jsonProvas = json.getAsJsonArray("criterios");
//		
//		for (int i = 0; i < jsonProvas.size(); i++)
//		{
//			String sigla = jsonProvas.get(i).getAsString();
//			ProvaEscrita prova = edital.pegaProvaEscritaSigla(sigla);
//			
//			if (prova != null)
//				provasEscritas.add(prova);
//		}
	}
}