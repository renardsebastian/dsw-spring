package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.unirio.dsw.selecaoppgi.model.User;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;
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
	
	/**
	 * Gera a representação JSON
	 */
	public JsonObject toJson()
	{
		JsonObject json = new JsonObject();
		json.addProperty("codigo", codigo);
		json.addProperty("nome", nome);
		json.addProperty("exigeProvaOral", exigeProvaOral);
		json.add("professores", geraRepresentacaoProfessores());
		json.add("provas", geraRepresentacaoProvasEscritas());
		return json;
	}

	/**
	 * Gera a representação JSON da lista de professores
	 */
	private JsonArray geraRepresentacaoProfessores()
	{
		JsonArray jsonProfessores = new JsonArray();
		
		for (User professor : professores)
		{
			JsonObject jsonProfessor = new JsonObject();
			jsonProfessor.addProperty("id", professor.getId());
			jsonProfessor.addProperty("nome", professor.getName());
			jsonProfessores.add(jsonProfessor);
		}
		
		return jsonProfessores;
	}

	/**
	 * Gera a representação JSON da lista de provas escritas
	 */
	private JsonArray geraRepresentacaoProvasEscritas()
	{
		JsonArray jsonProvas = new JsonArray();
		
		for (ProvaEscrita prova : provasEscritas)
			jsonProvas.add(new JsonPrimitive(prova.getSigla()));
		
		return jsonProvas;
	}

	/**
	 * Carrega a partir da representação JSON
	 */
	public void fromJson(JsonObject json, Edital edital, UserDAO userDAO)
	{
		this.codigo = json.get("codigo").getAsString();
		this.nome = json.get("nome").getAsString();
		this.exigeProvaOral = json.get("exigeProvaOral").getAsBoolean();
		carregaRepresentacaoProfessores(json, userDAO);
		carregaRepresentacaoProvasEscritas(json, edital);
	}

	/**
	 * Carrega a lista de professores a partir da representação JSON
	 */
	private void carregaRepresentacaoProfessores(JsonObject json, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("professores");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				professores.add(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritas(JsonObject json, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			String sigla = jsonProvas.get(i).getAsString();
			ProvaEscrita prova = edital.pegaProvaEscritaSigla(sigla);
			
			if (prova != null)
				provasEscritas.add(prova);
		}
	}
}