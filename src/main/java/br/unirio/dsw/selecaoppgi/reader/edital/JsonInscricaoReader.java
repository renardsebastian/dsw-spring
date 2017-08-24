package br.unirio.dsw.selecaoppgi.reader.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.usuario.User;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;

/**
 * Classe responsável por carregar um inscrição em edital a partir da sua representação JSON
 * 
 * @author marciobarros
 */
public class JsonInscricaoReader
{
	/**
	 * Carrega uma inscrição em edital a partir da representação JSON
	 */
	public boolean execute(JsonObject json, InscricaoEdital inscricao, Edital edital, UserDAO userDAO)
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

		carregaRepresentacaoProjetosPesquisa(json, inscricao, edital, userDAO);
		carregaRepresentacaoProvasEscritas(json, edital);
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
	private boolean carregaRepresentacaoProjetosPesquisa(JsonObject json, InscricaoEdital inscricao, Edital edital, UserDAO userDAO)
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
			
			if (!carregaRepresentacaoCriteriosAlinhamento(jsonProjeto, inscricaoProjeto, edital, userDAO))
				return false;
		}
		
		return true;
	}

	/**
	 * Carrega a lista de avaliações de critérios de alinhamento em projetos de pesquisa a partir da representação JSON
	 */
	private boolean carregaRepresentacaoCriteriosAlinhamento(JsonObject json, InscricaoProjetoPesquisa inscricaoProjeto, Edital edital, UserDAO userDAO)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criterios");
		
		for (int i = 0; i < jsonCriterios.size(); i++)
		{
			JsonObject jsonCriterio = jsonCriterios.get(i).getAsJsonObject();
			// TODO continuar daqui ...
			
			String codigo = jsonCriterio.get("codigo").getAsString();
			ProjetoPesquisa projeto = edital.pegaProjetoPesquisaCodigo(codigo);
			
			if (projeto == null)
				return false;
			
			String intencoes = jsonCriterio.get("intencoes").getAsString();
//			InscricaoProjetoPesquisa inscricaoProjeto = inscricao.adicionaInscricaoProjetoPesquisa(projeto, intencoes);
		}
		
		return true;
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritas(JsonObject json, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			JsonObject jsonProva = jsonProvas.get(i).getAsJsonObject();
			ProvaEscrita prova = carregaRepresentacaoProvaEscrita(jsonProva);
			edital.adicionaProvasEscrita(prova);
		}
	}

	/**
	 * Carrega uma prova escrita a partir da representação JSON
	 */
	private ProvaEscrita carregaRepresentacaoProvaEscrita(JsonObject json)
	{
		ProvaEscrita prova = new ProvaEscrita();
		prova.setCodigo(json.get("codigo").getAsString());
		prova.setNome(json.get("nome").getAsString());
		prova.setDispensavel(json.get("dispensavel").getAsBoolean());
		prova.setNotaMinimaAprovacao(json.get("notaMinima").getAsInt());
		
		JsonArray jsonQuestoes = json.getAsJsonArray("questoes");
		
		for (int i = 0; i < jsonQuestoes.size(); i++)
		{
			int peso = jsonQuestoes.get(i).getAsInt();
			prova.adicionaQuestao(peso);
		}
		
		return prova;
	}

	/**
	 * Carrega a lista de professores de um projeto de pesquisa a partir da representação JSON
	 */
	private void carregaRepresentacaoProfessoresProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("professores");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				projeto.adicionaProfessor(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritasProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			String codigo = jsonProvas.get(i).getAsString();
			ProvaEscrita prova = edital.pegaProvaEscritaCodigo(codigo);
			
			if (prova != null)
				projeto.adicionaProvaEscrita(prova);
		}
	}

	/**
	 * Carrega a lista de critérios de alinhamento a partir da representação JSON
	 */
	private void carregaRepresentacaoCriteriosAlinhamento(JsonObject json, Edital edital)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criterios");
		
		for (int i = 0; i < jsonCriterios.size(); i++)
		{
			JsonObject jsonCriterio = jsonCriterios.get(i).getAsJsonObject();
			CriterioAlinhamento criterio = carregaRepresentacaoCriterioAlinhamento(jsonCriterio);
			edital.adicionaCriterioAlinhamento(criterio);
		}
	}

	/**
	 * Carrega um criterio de alinhamento de pesquisa a partir da representação JSON
	 */
	private CriterioAlinhamento carregaRepresentacaoCriterioAlinhamento(JsonObject json)
	{
		CriterioAlinhamento criterio = new CriterioAlinhamento();
		criterio.setCodigo(json.get("codigo").getAsString());
		criterio.setNome(json.get("nome").getAsString());
		criterio.setPesoComProvaOral(json.get("pesoComProvaOral").getAsInt());
		criterio.setPesoSemProvaOral(json.get("pesoSemProvaOral").getAsInt());
		criterio.setPertenceProvaOral(json.get("pertenceProvaOral").getAsBoolean());
		
		JsonArray jsonSubcriterios = json.getAsJsonArray("subcriterios");
		
		for (int i = 0; i < jsonSubcriterios.size(); i++)
		{
			JsonObject jsonSubcriterio = jsonSubcriterios.get(i).getAsJsonObject();
			SubcriterioAlinhamento subcriterio = carregaRepresentacaoSubcriterioAlinhamento(jsonSubcriterio); 
			criterio.adicionaSubcriterio(subcriterio);
		}
		
		return criterio;
	}

	/**
	 * Carrega um subcriterio de alinhamento de pesquisa a partir da representação JSON
	 */
	private SubcriterioAlinhamento carregaRepresentacaoSubcriterioAlinhamento(JsonObject json)
	{
		SubcriterioAlinhamento subcriterio = new SubcriterioAlinhamento();
		subcriterio.setCodigo(json.get("codigo").getAsString());
		subcriterio.setNome(json.get("nome").getAsString());
		subcriterio.setDescricao(json.get("descricao").getAsString());
		subcriterio.setPeso(json.get("peso").getAsInt());
		return subcriterio;
	}
}