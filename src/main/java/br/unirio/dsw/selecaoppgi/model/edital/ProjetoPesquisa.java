package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.unirio.dsw.selecaoppgi.model.User;
import lombok.Getter;
import lombok.Setter;

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
	public void formJson(JsonObject json)
	{
		this.codigo = json.get("codigo").getAsString();
		this.nome = json.get("nome").getAsString();
		this.exigeProvaOral = json.get("exigeProvaOral").getAsBoolean();
		// TODO: Carrega os professores - como representar?
		// TODO: Carrega as provas escritas - receber array completo
	}
}