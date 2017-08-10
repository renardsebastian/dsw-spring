package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma prova escrita
 * 
 * @author marciobarros
 */
public class ProvaEscrita
{
	private @Getter @Setter String sigla;
	private @Getter @Setter String nome;
	private @Getter @Setter boolean dispensavel;
	private @Getter @Setter int notaMinimaAprovacao;
	private List<Integer> pesosQuestoes;

	/**
	 * Inicializa uma prova escrita
	 */
	public ProvaEscrita()
	{
		this.sigla = "";
		this.nome = "";
		this.dispensavel = false;
		this.notaMinimaAprovacao = 0;
		this.pesosQuestoes = new ArrayList<Integer>();
	}

	/**
	 * Conta o número de questões da prova
	 */
	public int contaQuestoes()
	{
		return pesosQuestoes.size();
	}

	/**
	 * Retorna o peso de uma questão, dado seu índice
	 */
	public int pegaPesoQuestaoIndice(int indice)
	{
		return pesosQuestoes.get(indice);
	}

	/**
	 * Adiciona uma questão na prova, dado seu peso
	 */
	public void adicionaQuestao(int peso)
	{
		pesosQuestoes.add(peso);
	}

	/**
	 * Remove uma questão, dado seu índice
	 */
	public void removeQuestao(int indice)
	{
		pesosQuestoes.remove(indice);
	}

	/**
	 * Gera a representação JSON
	 */
	public JsonObject toJson()
	{
		JsonObject json = new JsonObject();
		json.addProperty("sigla", sigla);
		json.addProperty("nome", nome);
		json.addProperty("dispensavel", dispensavel);
		json.addProperty("notaMinima", notaMinimaAprovacao);

		JsonArray jsonQuestoes = new JsonArray();
		
		for (Integer peso : pesosQuestoes)
			jsonQuestoes.add(new JsonPrimitive(peso));

		json.add("questoes", jsonQuestoes);
		return json;
	}

	/**
	 * Carrega a partir da representação JSON
	 */
	public void formJson(JsonObject json)
	{
		this.sigla = json.get("sigla").getAsString();
		this.nome = json.get("nome").getAsString();
		this.dispensavel = json.get("dispensavel").getAsBoolean();
		this.notaMinimaAprovacao = json.get("notaMinima").getAsInt();
		
		JsonArray jsonQuestoes = json.getAsJsonArray("questoes");
		
		for (int i = 0; i < jsonQuestoes.size(); i++)
		{
			int peso = jsonQuestoes.get(i).getAsInt();
			pesosQuestoes.add(peso);
		}
	}
}