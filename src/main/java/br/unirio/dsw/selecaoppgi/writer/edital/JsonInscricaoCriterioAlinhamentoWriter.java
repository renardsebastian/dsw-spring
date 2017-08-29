package br.unirio.dsw.selecaoppgi.writer.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoCriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoSubcriterioAlinhamento;

/**
 * Classe que publica os dados de uma avaliação de critério de alinhamento de um edital em formato JSON
 * 
 * @author Marcio Barros
 */
public class JsonInscricaoCriterioAlinhamentoWriter
{
	/**
	 * Gera a representação JSON de uma lista de avaliações de subcritérios de alinhamento
	 */
	public JsonArray geraRepresentacaoAvaliacoesSubcriteriosAlinhamento(AvaliacaoCriterioAlinhamento avaliacao)
	{
		JsonArray jsonAvaliacoes = new JsonArray();
		
		for (AvaliacaoSubcriterioAlinhamento avaliacaoSubcriterio : avaliacao.getAvaliacoesSubcriterioAlinhamento())
			jsonAvaliacoes.add(geraRepresentacaoAvaliacaoSubcriterioAlinhamento(avaliacaoSubcriterio));
		
		return jsonAvaliacoes;
	}

	/**
	 * Gera a representação JSON de uma avaliação de subcritério de alinhamento
	 */
	private JsonObject geraRepresentacaoAvaliacaoSubcriterioAlinhamento(AvaliacaoSubcriterioAlinhamento avaliacao)
	{
		JsonObject json = new JsonObject();
		json.addProperty("codigo", avaliacao.getSubcriterio().getCodigo());

		if (avaliacao.getNotaOriginal() != null)
		{
			json.addProperty("notaOriginal", avaliacao.getNotaOriginal());

			if (avaliacao.getNotaRecurso() != null)
				json.addProperty("notaRecurso", avaliacao.getNotaRecurso());
		}

		return json;
	}
}