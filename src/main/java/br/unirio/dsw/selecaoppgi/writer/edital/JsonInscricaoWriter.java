package br.unirio.dsw.selecaoppgi.writer.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoCriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoSubcriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoProjetoPesquisa;

/**
 * Classe que publica os dados de uma inscrição em edital em formato JSON
 * 
 * @author Marcio Barros
 */
public class JsonInscricaoWriter
{
	/**
	 * Gera a representação JSON
	 */
	public JsonObject execute(InscricaoEdital inscricao)
	{
		JsonObject json = new JsonObject();
		json.addProperty("id", inscricao.getId());
		json.addProperty("idCandidato", inscricao.getIdCandidato());
		json.addProperty("nomeCandidato", inscricao.getNomeCandidato());
		json.addProperty("idEdital", inscricao.getEdital().getId());
		json.addProperty("cotaNegros", inscricao.isCotaNegros());
		json.addProperty("cotaDeficientes", inscricao.isCotaDeficientes());
		
		geraRepresentacaoCondicional(json, "homologacaoOriginal", inscricao.getHomologadoOriginal(), inscricao.getJustificativaHomologacaoOriginal());
		geraRepresentacaoCondicional(json, "homologacaoRecurso", inscricao.getHomologadoRecurso(), inscricao.getJustificativaHomologacaoRecurso());
		geraRepresentacaoCondicional(json, "dispensadoOriginal", inscricao.getDispensadoProvaOriginal(), inscricao.getJustificativaDispensaOriginal());
		geraRepresentacaoCondicional(json, "dispensadoRecurso", inscricao.getDispensadoProvaRecurso(), inscricao.getJustificativaDispensaRecurso());
		
		json.add("projetos", geraRepresentacaoInscricoesProjetoPesquisa(inscricao));
		json.add("provas", geraRepresentacaoAvaliacaoProvasEscritas(inscricao));
		return json;
	}

	/**
	 * Gera a representação JSON de uma condição com justificativa para negativos
	 */
	private void geraRepresentacaoCondicional(JsonObject json, String nome, Boolean condicao, String justificativa)
	{
		if (condicao != null)
		{
			JsonObject jsonCondicao = new JsonObject();
			jsonCondicao.addProperty("condicao", condicao);
			
			if (!condicao)
				jsonCondicao.addProperty("justificativa", justificativa);
			
			json.add(nome, jsonCondicao);
		}
	}

	/**
	 * Gera a representação JSON das inscrições em projetos de pesquisa
	 */
	private JsonArray geraRepresentacaoInscricoesProjetoPesquisa(InscricaoEdital inscricao)
	{
		JsonArray jsonProjetos = new JsonArray();
		
		for (InscricaoProjetoPesquisa inscricaoProjeto : inscricao.getInscricoesProjetoPesquisa())
			jsonProjetos.add(geraRepresentacaoInscricaoProjetoPesquisa(inscricaoProjeto));
		
		return jsonProjetos;
	}
	
	/**
	 * Gera a representação JSON de uma inscrição em projeto de pesquisa
	 */
	private JsonObject geraRepresentacaoInscricaoProjetoPesquisa(InscricaoProjetoPesquisa inscricao)
	{
		JsonObject json = new JsonObject();
		json.addProperty("codigo", inscricao.getProjetoPesquisa().getCodigo());
		json.addProperty("intencoes", inscricao.getIntencoes());
		json.add("criterios", geraRepresentacaoAvaliacoesCriteriosAlinhamento(inscricao));
		return json;
	}

	/**
	 * Gera a representação JSON de uma lista de avaliações de critérios de alinhamento
	 */
	private JsonArray geraRepresentacaoAvaliacoesCriteriosAlinhamento(InscricaoProjetoPesquisa inscricao)
	{
		JsonArray jsonAvaliacoes = new JsonArray();
		
		for (AvaliacaoCriterioAlinhamento avaliacao : inscricao.getAvaliacoesCriterioAlinhamento())
			jsonAvaliacoes.add(geraRepresentacaoAvaliacaoCriterioAlinhamento(avaliacao));
		
		return jsonAvaliacoes;
	}

	/**
	 * Gera a representação JSON de uma avaliação de critério de alinhamento
	 */
	private JsonObject geraRepresentacaoAvaliacaoCriterioAlinhamento(AvaliacaoCriterioAlinhamento avaliacao)
	{
		JsonObject json = new JsonObject();
		json.addProperty("codigo", avaliacao.getCriterioAlinhamento().getCodigo());

		if (avaliacao.getPresenteProvaOral() != null)
			json.addProperty("presente", avaliacao.getPresenteProvaOral());

		json.addProperty("justificativaOriginal", avaliacao.getJustificativaNotasOriginal());
		json.addProperty("justificativaRecurso", avaliacao.getJustificativaNotasRecurso());
		json.add("subcriterios", geraRepresentacaoAvaliacoesSubcriteriosAlinhamento(avaliacao));
		return json;
	}

	/**
	 * Gera a representação JSON de uma lista de avaliações de subcritérios de alinhamento
	 */
	private JsonArray geraRepresentacaoAvaliacoesSubcriteriosAlinhamento(AvaliacaoCriterioAlinhamento avaliacao)
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

	/**
	 * Gera a representação JSON da lista de avaliações de provas escritas
	 */
	private JsonArray geraRepresentacaoAvaliacaoProvasEscritas(InscricaoEdital inscricao)
	{
		JsonArray jsonProvas = new JsonArray();
		
		for (AvaliacaoProvaEscrita avaliacao : inscricao.getAvaliacoesProvasEscritas())
			jsonProvas.add(geraRepresentacaoAvaliacaoProvaEscrita(avaliacao));
		
		return jsonProvas;
	}

	/**
	 * Gera a representação JSON de uma avaliação de prova escrita
	 */
	private JsonObject geraRepresentacaoAvaliacaoProvaEscrita(AvaliacaoProvaEscrita avaliacao)
	{
		JsonObject json = new JsonObject();
		json.addProperty("sigla", avaliacao.getProvaEscrita().getCodigo());

		if (avaliacao.getPresente() != null)
		{
			json.addProperty("presente", avaliacao.getPresente());

			if (avaliacao.getPresente())
			{
				json.add("notasOriginal", geraRepresentacaoNotasOriginais(avaliacao));
				json.add("notasRecurso", geraRepresentacaoNotasRecurso(avaliacao));
			}
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