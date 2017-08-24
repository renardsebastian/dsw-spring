package br.unirio.dsw.selecaoppgi.reader.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoCriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoSubcriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoProjetoPesquisa;

/**
 * Classe responsável por carregar uma inscrição em edital a partir da sua representação JSON
 * 
 * @author marciobarros
 */
public class JsonInscricaoReader
{
	/**
	 * Carrega uma inscrição em edital a partir da representação JSON
	 */
	public boolean execute(JsonObject json, InscricaoEdital inscricao, Edital edital)
	{
		int id = json.get("id").getAsInt();
		inscricao.setId(id);

		int idCandidato = json.get("idCandidato").getAsInt();
		inscricao.setIdCandidato(idCandidato);

		String nomeCandidato = json.get("nomeCandidato").getAsString();
		inscricao.setNomeCandidato(nomeCandidato);

		boolean cotaNegros = json.get("cotaNegros").getAsBoolean();
		inscricao.setCotaNegros(cotaNegros);

		boolean cotaDeficientes = json.get("cotaDeficientes").getAsBoolean();
		inscricao.setCotaDeficientes(cotaDeficientes);
		
		carregaRepresentacaoHomologacaoOriginal(json, inscricao);		
		carregaRepresentacaoHomologacaoRecurso(json, inscricao);
		carregaRepresentacaoDispensaOriginal(json, inscricao);
		carregaRepresentacaoDispensaRecurso(json, inscricao);

		if (!carregaRepresentacaoInscricaoProjetosPesquisa(json, inscricao, edital))
			return false;
		
		if (!carregaRepresentacaoAvaliacaoProvasEscritas(json, inscricao, edital))
			return false;
		
		return true;
	}

	/**
	 * Carrega a homologação original a partir da sua representação JSON
	 */
	private void carregaRepresentacaoHomologacaoOriginal(JsonObject json, InscricaoEdital inscricao)
	{
		JsonObject jsonHomologacaoOriginal = json.getAsJsonObject("homologacaoOriginal");
		
		if (jsonHomologacaoOriginal != null)
		{
			boolean homologadoOriginal = jsonHomologacaoOriginal.get("condicao").getAsBoolean();
			inscricao.setHomologadoOriginal(homologadoOriginal);

			String justificativaOriginal = jsonHomologacaoOriginal.get("justificativa").getAsString();
			inscricao.setJustificativaHomologacaoOriginal(justificativaOriginal);
		}
	}

	/**
	 * Carrega a homologação por recurso a partir da sua representação JSON
	 */
	private void carregaRepresentacaoHomologacaoRecurso(JsonObject json, InscricaoEdital inscricao)
	{
		JsonObject jsonHomologacaoRecurso = json.getAsJsonObject("homologacaoRecurso");
		
		if (jsonHomologacaoRecurso != null)
		{
			boolean homologadoRecurso = jsonHomologacaoRecurso.get("condicao").getAsBoolean();
			inscricao.setHomologadoRecurso(homologadoRecurso);

			String justificativaRecurso = jsonHomologacaoRecurso.get("justificativa").getAsString();
			inscricao.setJustificativaHomologacaoRecurso(justificativaRecurso);
		}
	}

	/**
	 * Carrega a dispensa de prova original a partir da sua representação JSON
	 */
	private void carregaRepresentacaoDispensaOriginal(JsonObject json, InscricaoEdital inscricao)
	{
		JsonObject jsonDispensadoOriginal = json.getAsJsonObject("dispensadoOriginal");
		
		if (jsonDispensadoOriginal != null)
		{
			boolean dispensadoOriginal = jsonDispensadoOriginal.get("condicao").getAsBoolean();
			inscricao.setDispensadoProvaOriginal(dispensadoOriginal);

			String justificativaOriginal = jsonDispensadoOriginal.get("justificativa").getAsString();
			inscricao.setJustificativaDispensaOriginal(justificativaOriginal);
		}
	}

	/**
	 * Carrega a dispensa de prova por recurso a partir da sua representação JSON
	 */
	private void carregaRepresentacaoDispensaRecurso(JsonObject json, InscricaoEdital inscricao)
	{
		JsonObject jsonDispensadoRecurso = json.getAsJsonObject("dispensadoRecurso");
		
		if (jsonDispensadoRecurso != null)
		{
			boolean dispensadoRecurso = jsonDispensadoRecurso.get("condicao").getAsBoolean();
			inscricao.setDispensadoProvaRecurso(dispensadoRecurso);

			String justificativaRecurso = jsonDispensadoRecurso.get("justificativa").getAsString();
			inscricao.setJustificativaDispensaRecurso(justificativaRecurso);
		}
	}

	/**
	 * Carrega a lista de inscrições em projetos de pesquisa a partir da representação JSON
	 */
	private boolean carregaRepresentacaoInscricaoProjetosPesquisa(JsonObject json, InscricaoEdital inscricao, Edital edital)
	{
		JsonArray jsonProjetos = json.getAsJsonArray("projetos");
		
		for (int i = 0; i < jsonProjetos.size(); i++)
		{
			JsonObject jsonProjeto = jsonProjetos.get(i).getAsJsonObject();
			
			String codigo = jsonProjeto.get("codigo").getAsString();
			ProjetoPesquisa projeto = edital.pegaProjetoPesquisaCodigo(codigo);
			
			if (projeto == null)
				return false;
			
			String intencoes = jsonProjeto.get("intencoes").getAsString();
			InscricaoProjetoPesquisa inscricaoProjeto = inscricao.adicionaInscricaoProjetoPesquisa(projeto, intencoes);
			
			if (!carregaRepresentacaoCriteriosAlinhamento(jsonProjeto, inscricaoProjeto, edital))
				return false;
		}
		
		return true;
	}

	/**
	 * Carrega a lista de avaliações de critérios de alinhamento em projetos de pesquisa a partir da representação JSON
	 */
	private boolean carregaRepresentacaoCriteriosAlinhamento(JsonObject json, InscricaoProjetoPesquisa inscricaoProjeto, Edital edital)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criterios");
		
		for (int i = 0; i < jsonCriterios.size(); i++)
		{
			JsonObject jsonCriterio = jsonCriterios.get(i).getAsJsonObject();
			
			String codigo = jsonCriterio.get("codigo").getAsString();
			CriterioAlinhamento criterio = edital.pegaCriterioAlinhamentoCodigo(codigo);
			
			if (criterio == null)
				return false;
			
			AvaliacaoCriterioAlinhamento avaliacaoCriterio = inscricaoProjeto.pegaAvaliacaoCriterioAlinhamento(criterio);
			
			if (jsonCriterio.has("presente"))
			{
				boolean presente = jsonCriterio.get("presente").getAsBoolean();
				avaliacaoCriterio.setPresenteProvaOral(presente);
			}

			String justificativaOriginal = jsonCriterio.get("justificativaOriginal").getAsString();
			avaliacaoCriterio.setJustificativaNotasOriginal(justificativaOriginal);
			
			String justificativaRecurso = jsonCriterio.get("justificativaRecurso").getAsString();
			avaliacaoCriterio.setJustificativaNotasRecurso(justificativaRecurso);
			
			if (!carregaRepresentacaoSubcriteriosAlinhamento(jsonCriterio, avaliacaoCriterio, edital))
				return false;
		}
		
		return true;
	}

	/**
	 * Carrega a lista de avaliações de subcritérios de alinhamento em projetos de pesquisa a partir da representação JSON
	 */
	private boolean carregaRepresentacaoSubcriteriosAlinhamento(JsonObject json, AvaliacaoCriterioAlinhamento avaliacaoCriterio, Edital edital)
	{
		JsonArray jsonSubcriterios = json.getAsJsonArray("subcriterios");
		
		for (int i = 0; i < jsonSubcriterios.size(); i++)
		{
			JsonObject jsonSubcriterio = jsonSubcriterios.get(i).getAsJsonObject();
			
			String codigo = jsonSubcriterio.get("codigo").getAsString();
			SubcriterioAlinhamento subcriterio = avaliacaoCriterio.getCriterioAlinhamento().pegaSubcriterioAlinhamentoCodigo(codigo);
			
			if (subcriterio == null)
				return false;
			
			AvaliacaoSubcriterioAlinhamento avaliacaoSubcriterio = avaliacaoCriterio.pegaAvaliacaoSubcriterioAlinhamento(subcriterio);
			
			if (avaliacaoSubcriterio == null)
				return false;
			
			if (jsonSubcriterio.has("notaOriginal"))
			{
				int notaOriginal = jsonSubcriterio.get("notaOriginal").getAsInt();
				avaliacaoSubcriterio.setNotaOriginal(notaOriginal);
			}
			
			if (jsonSubcriterio.has("notaRecurso"))
			{
				int notaRecurso = jsonSubcriterio.get("notaRecurso").getAsInt();
				avaliacaoSubcriterio.setNotaRecurso(notaRecurso);
			}
		}
		
		return true;
	}

	/**
	 * Carrega a lista de avaliações de provas escritas a partir da representação JSON
	 */
	private boolean carregaRepresentacaoAvaliacaoProvasEscritas(JsonObject json, InscricaoEdital inscricao, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			JsonObject jsonProva = jsonProvas.get(i).getAsJsonObject();

			String codigo = jsonProva.get("codigo").getAsString();
			ProvaEscrita prova = edital.pegaProvaEscritaCodigo(codigo);
			
			if (prova == null)
				return false;
			
			AvaliacaoProvaEscrita avaliacaoProva = inscricao.pegaAvaliacaoProvaEscrita(prova);
			
			if (avaliacaoProva == null)
				return false;
			
			if (jsonProva.has("presente"))
			{
				boolean presente = jsonProva.get("presente").getAsBoolean();
				avaliacaoProva.setPresente(presente);
				
				if (presente)
				{
					if (!carregaRepresentacaoNotasOriginaisProvaEscrita(jsonProva, prova, avaliacaoProva))
						return false;

					if (!carregaRepresentacaoNotasRecursoProvaEscrita(jsonProva, prova, avaliacaoProva))
						return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Carrega as notas originais das avaliações de provas escritas a partir da sua representação JSON
	 */
	private boolean carregaRepresentacaoNotasOriginaisProvaEscrita(JsonObject jsonProva, ProvaEscrita prova, AvaliacaoProvaEscrita avaliacaoProva)
	{
		JsonArray jsonNotas = jsonProva.getAsJsonArray("notasOriginal");
		
		if (jsonNotas.size() != prova.contaQuestoes())
			return false;
		
		for (int i = 0; i < prova.contaQuestoes(); i++)
		{
			int nota = jsonNotas.get(i).getAsInt();
			
			if (nota >= 0)
				avaliacaoProva.setNotaOriginalQuestao(i, nota);
		}
		
		return true;
	}

	/**
	 * Carrega as notas de recurso das avaliações de provas escritas a partir da sua representação JSON
	 */
	private boolean carregaRepresentacaoNotasRecursoProvaEscrita(JsonObject jsonProva, ProvaEscrita prova, AvaliacaoProvaEscrita avaliacaoProva)
	{
		JsonArray jsonNotas = jsonProva.getAsJsonArray("notasRecurso");
		
		if (jsonNotas.size() != prova.contaQuestoes())
			return false;
		
		for (int i = 0; i < prova.contaQuestoes(); i++)
		{
			int nota = jsonNotas.get(i).getAsInt();
			
			if (nota >= 0)
				avaliacaoProva.setNotaRecursoQuestao(i, nota);
		}
		
		return true;
	}
}