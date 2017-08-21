package br.unirio.dsw.selecaoppgi.writer.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.usuario.User;

/**
 * Classe que publica os dados de uma inscrição em formato JSON
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
//		json.add("provas", geraRepresentacaoProvasEscritas(inscricao));
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
		// TODO continuar daqui ...
		
//		for (ProjetoPesquisa projeto : edital.getProjetosPesquisa())
//			jsonProjetos.add(geraRepresentacaoProjetoPesquisa(projeto));
		
		return jsonProjetos;
	}
	
	/**
	 * Gera a representação JSON de um projeto de pesquisa
	 */
	private JsonObject geraRepresentacaoProjetoPesquisa(ProjetoPesquisa projeto)
	{
		JsonObject json = new JsonObject();
		json.addProperty("codigo", projeto.getCodigo());
		json.addProperty("nome", projeto.getNome());
		json.addProperty("exigeProvaOral", projeto.isExigeProvaOral());
		json.add("professores", geraRepresentacaoProfessoresProjetoPesquisa(projeto));
		json.add("provas", geraRepresentacaoProvasEscritasProjetoPesquisa(projeto));
		return json;
	}

	/**
	 * Gera a representação JSON da lista de provas escritas
	 */
	private JsonArray geraRepresentacaoProvasEscritas(Edital edital)
	{
		JsonArray jsonProvas = new JsonArray();
		
		for (ProvaEscrita prova : edital.getProvasEscritas())
			jsonProvas.add(geraRepresentacaoProvaEscrita(prova));
		
		return jsonProvas;
	}

	/**
	 * Gera a representação JSON de uma prova escrita
	 */
	private JsonObject geraRepresentacaoProvaEscrita(ProvaEscrita prova)
	{
		JsonObject json = new JsonObject();
		json.addProperty("sigla", prova.getSigla());
		json.addProperty("nome", prova.getNome());
		json.addProperty("dispensavel", prova.isDispensavel());
		json.addProperty("notaMinima", prova.getNotaMinimaAprovacao());

		JsonArray jsonQuestoes = new JsonArray();
		
		for (Integer peso : prova.getPesosQuestoes())
			jsonQuestoes.add(new JsonPrimitive(peso));

		json.add("questoes", jsonQuestoes);
		return json;
	}

	/**
	 * Gera a representação JSON da lista de professores
	 */
	private JsonArray geraRepresentacaoProfessoresProjetoPesquisa(ProjetoPesquisa projeto)
	{
		JsonArray jsonProfessores = new JsonArray();
		
		for (User professor : projeto.getProfessores())
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
	private JsonArray geraRepresentacaoProvasEscritasProjetoPesquisa(ProjetoPesquisa projeto)
	{
		JsonArray jsonProvas = new JsonArray();
		
		for (ProvaEscrita prova : projeto.getProvasEscritas())
			jsonProvas.add(new JsonPrimitive(prova.getSigla()));
		
		return jsonProvas;
	}

	/**
	 * Gera a representação JSON da lista de projetos de pesquisa
	 */
	private JsonArray geraRepresentacaoCriteriosAlinhamento(Edital edital)
	{
		JsonArray jsonCriterios = new JsonArray();
		
		for (CriterioAlinhamento criterio : edital.getCriteriosAlinhamento())
			jsonCriterios.add(geraRepresentacaoCriterioAlinhamento(criterio));
		
		return jsonCriterios;
	}

	/**
	 * Gera a representação JSON de um critério de alinhamento
	 */
	private JsonObject geraRepresentacaoCriterioAlinhamento(CriterioAlinhamento criterio)
	{
		JsonObject json = new JsonObject();
		json.addProperty("nome", criterio.getNome());
		json.addProperty("pesoComProvaOral", criterio.getPesoComProvaOral());
		json.addProperty("pesoSemProvaOral", criterio.getPesoSemProvaOral());
		json.addProperty("pertenceProvaOral", criterio.isPertenceProvaOral());

		JsonArray jsonSubcriterios = new JsonArray();
		
		for (SubcriterioAlinhamento subcriterio : criterio.getSubcriterios())
			jsonSubcriterios.add(geraRepresentacaoSubcriterioAlinhamento(subcriterio));

		json.add("subcriterios", jsonSubcriterios);
		return json;
	}

	/**
	 * Gera a representação JSON de um subcritério de avaliação de alinhamento
	 */
	private JsonObject geraRepresentacaoSubcriterioAlinhamento(SubcriterioAlinhamento subcriterio)
	{
		JsonObject json = new JsonObject();
		json.addProperty("nome", subcriterio.getNome());
		json.addProperty("descricao", subcriterio.getDescricao());
		json.addProperty("peso", subcriterio.getPeso());
		return json;
	}
}