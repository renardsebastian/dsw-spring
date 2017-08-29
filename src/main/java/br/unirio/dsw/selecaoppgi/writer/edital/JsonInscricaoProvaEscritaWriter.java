package br.unirio.dsw.selecaoppgi.writer.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoProvaEscrita;

/**
 * Classe que publica os dados de uma inscrição em edital em formato JSON
 * 
 * @author Marcio Barros
 */
public class JsonInscricaoProvaEscritaWriter
{
	/**
	 * Gera a representação JSON de uma avaliação de prova escrita
	 */
	public JsonObject geraRepresentacaoAvaliacaoProvaEscrita(AvaliacaoProvaEscrita avaliacao)
	{
		JsonObject json = new JsonObject();

		if (avaliacao.getPresente() != null && avaliacao.getPresente())
		{
			json.add("notasOriginal", geraRepresentacaoNotasOriginais(avaliacao));
			json.add("notasRecurso", geraRepresentacaoNotasRecurso(avaliacao));
		}

		return json;
	}

	/**
	 * Gera a representação JSON das notas originais de uma prova escrita
	 */
	private JsonArray geraRepresentacaoNotasOriginais(AvaliacaoProvaEscrita avaliacao)
	{
		JsonArray jsonNotasOriginal = new JsonArray();
		
		for (int i = 0; i < avaliacao.getProvaEscrita().contaQuestoes(); i++)
		{
			if (avaliacao.possuiNotaOriginalQuestao(i))
				jsonNotasOriginal.add(new JsonPrimitive(avaliacao.getNotaOriginalQuestao(i)));
			else
				jsonNotasOriginal.add(new JsonPrimitive(-1));
		}
		
		return jsonNotasOriginal;
	}

	/**
	 * Gera a representação JSON das notas de recurso de uma prova escrita
	 */
	private JsonArray geraRepresentacaoNotasRecurso(AvaliacaoProvaEscrita avaliacao)
	{
		JsonArray jsonNotasRecurso = new JsonArray();
		
		for (int i = 0; i < avaliacao.getProvaEscrita().contaQuestoes(); i++)
		{
			if (avaliacao.possuiNotaRecursoQuestao(i))
				jsonNotasRecurso.add(new JsonPrimitive(avaliacao.getNotaRecursoQuestao(i)));
			else
				jsonNotasRecurso.add(new JsonPrimitive(-1));
		}
		
		return jsonNotasRecurso;
	}
}