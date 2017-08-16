package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um critério de alinhamento dentro de um edital
 * 
 * @author marciobarros
 */
public class CriterioAlinhamento
{
	private @Getter @Setter String nome;
	private @Getter @Setter int pesoComProvaOral;
	private @Getter @Setter int pesoSemProvaOral;
	private @Getter @Setter boolean pertenceProvaOral;
	private List<SubcriterioAlinhamento> subcriterios;
	
	/**
	 * Inicializa o critério
	 */
	public CriterioAlinhamento()
	{
		this.nome = "";
		this.pesoComProvaOral = 0;
		this.pesoSemProvaOral = 0;
		this.pertenceProvaOral = false;
		this.subcriterios = new ArrayList<SubcriterioAlinhamento>();
	}
	
	/**
	 * Retorna o número de subcritérios do critério
	 */
	public int contaSubcriterios()
	{
		return subcriterios.size();
	}
	
	/**
	 * Retorna um subcritério de alinhamento, dado seu índice
	 */
	public SubcriterioAlinhamento pegaSubcriterioIndice(int indice)
	{
		return subcriterios.get(indice);
	}

	/**
	 * Retorna a lista de subcritérios de avaliação do alinhamento
	 */
	public Iterable<SubcriterioAlinhamento> getSubcriterios()
	{
		return subcriterios;
	}
	
	/**
	 * Adiciona um subcritério de alinhamento
	 */
	public void adicionaSubcriterio(SubcriterioAlinhamento subcriterio)
	{
		subcriterios.add(subcriterio);
	}

	/**
	 * Carrega a partir da representação JSON
	 */
	public void fromJson(JsonObject json)
	{
		this.nome = json.get("nome").getAsString();
		this.pesoComProvaOral = json.get("pesoComProvaOral").getAsInt();
		this.pesoSemProvaOral = json.get("pesoSemProvaOral").getAsInt();
		this.pertenceProvaOral = json.get("pertenceProvaOral").getAsBoolean();
		
		JsonArray jsonSubcriterios = json.getAsJsonArray("subcriterios");
		
		for (int i = 0; i < jsonSubcriterios.size(); i++)
		{
			JsonObject jsonSubcriterio = jsonSubcriterios.get(i).getAsJsonObject();
			SubcriterioAlinhamento subcriterio = new SubcriterioAlinhamento(); 
			subcriterio.formJson(jsonSubcriterio);
			subcriterios.add(subcriterio);
		}
	}
}