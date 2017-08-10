package br.unirio.dsw.selecaoppgi.model.edital;

import com.google.gson.JsonObject;

import lombok.Data;

/**
 * Classe que representa um subcriterio da prova de alinhamento de pesquisa
 * 
 * @author marciobarros
 */
public @Data class SubcriterioAlinhamento
{
	private String nome;
	private String descricao;
	private int peso;
	
	/**
	 * Inicializa o subcritério
	 */
	public SubcriterioAlinhamento()
	{
		this.nome = "";
		this.descricao = "";
		this.peso = 0;
	}

	/**
	 * Gera a representação JSON
	 */
	public JsonObject toJson()
	{
		JsonObject json = new JsonObject();
		json.addProperty("nome", nome);
		json.addProperty("descricao", descricao);
		json.addProperty("peso", peso);
		return json;
	}

	/**
	 * Carrega a partir da representação JSON
	 */
	public void formJson(JsonObject json)
	{
		this.nome = json.get("nome").getAsString();
		this.descricao = json.get("descricao").getAsString();
		this.peso = json.get("peso").getAsInt();
	}
}