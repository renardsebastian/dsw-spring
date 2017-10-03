package br.unirio.dsw.selecaoppgi.service.dao;

import org.springframework.stereotype.Service;

import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;

/**
 * Classe responsavel pela persistencia de inscrições em edital de seleção
 * 
 * @author Marcio Barros
 */
@Service("inscricaoDAO")
public class InscricaoDAO extends AbstractDAO
{
	/**
	 * Registra uma nova inscrição, incluindo os projetos de pesquisa
	 */
	public boolean registraInscricao(InscricaoEdital inscricao)
	{
//		Cria um registro com os campos abaixo na tabela de Inscricao
//
//		* dataRegistro
//		* dataAtualizacao
//		* idEdital
//		* idCandidato
//		* cotaNegros
//		* cotaDeficientes
//		* jsonProjetos: vetor de codigo do projeto de pesquisa e texto descrevendo interesses de pesquisa
//
//		Cria os registros de presença e notas na prova escrita (notas e presencao nulas, inicialmente)
//
//		Cria os registros de avaliação de critérios

		return false;
	}
	
	public boolean homologacaoInicial(int idInscricao)
	{
		// Muda o campo homologadoInicial para TRUE e limpa o campo justificativaHomologacaoInicial
		return false;
	}
	
	public boolean recusaHomologacaoInicial(int idInscricao, String justificativa)
	{
		// Muda o campo homologadoInicial para FALSE e preenche o campo justificativaHomologacaoInicial
		return false;
	}
	
	public boolean homologacaoRecurso(int idInscricao)
	{
		// Muda o campo homologadoRecurso para TRUE e limpa o campo justificativaHomologacaoRecurso
		// Somente se o campo homologadoInicial estiver FALSE
		return false;
	}
	
	public boolean recusaHomologacaoRecurso(int idInscricao, String justificativa)
	{
		// Muda o campo homologadoRecurso para FALSE e preenche o campo justificativaHomologacaoRecurso
		// Somente se o campo homologadoInicial estiver FALSE
		return false;
	}
	
	public boolean dispensaProvaInicial(int idInscricao)
	{
		// Muda o campo dispensadoProvaInicial para TRUE e limpa o campo justificativaDispensaInicial
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		return false;
	}
	
	public boolean recusaDispensaProvaInicial(int idInscricao, String justificativa)
	{
		// Muda o campo dispensadoProvaInicial para FALSE e preenche o campo justificativaDispensaInicial
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		return false;
	}
	
	public boolean dispensaProvaRecurso(int idInscricao)
	{
		// Muda o campo dispensadoProvaRecurso para TRUE e limpa o campo justificativaDispensaRecurso
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE
		return false;
	}
	
	public boolean recusaDispensaProvaRecurso(int idInscricao, String justificativa)
	{
		// Muda o campo dispensadoProvaRecurso para FALSE e preenche o campo justificativaDispensaRecurso
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE
		return false;
	}
	
	public boolean indicaPresencaProvaEscrita(int idInscricao, String codigoProva)
	{
		// Muda o campo presente para TRUE no registro da prova escrita associada à inscrição
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		return false;
	}
	
	public boolean indicaAusenciaProvaEscrita(int idInscricao, String codigoProva)
	{
		// Muda o campo presente para FALSE no registro da prova escrita associada à inscrição
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		return false;
	}
	
	public boolean indicaNotasProvaEscritaInicial(int idInscricao, String codigoProva, int[] notasQuestoes, int notaFinal)
	{
		// Muda os campos jsonQuestoes e notaFinal de acordo com os parâmetros
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		// Somente se a presença na prova estiver TRUE
		return false;
	}
	
	public boolean indicaNotasProvaEscritaRecurso(int idInscricao, String codigoProva, int[] notasQuestoes, int notaFinal)
	{
		// Muda os campos jsonQuestoes e notaFinal de acordo com os parâmetros
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		// Somente se a presença na prova estiver TRUE
		return false;
	}
	
	public boolean indicaPresencaProvaOral(int idInscricao, String codigoProjetoPesquisa)
	{
		// Muda o campo presenteProvaOral para TRUE no registro da prova de alinhamento associada à inscrição e projeto de pesquisa
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		// Somente se a nota final de todas as provas escritas for maior do que a nota mínima para aprovação
		// Somente se o projeto exigir prova oral
		return false;
	}
	
	public boolean indicaAusenciaProvaOral(int idInscricao, String codigoProjetoPesquisa)
	{
		// Muda o campo presenteProvaOral para FALSE no registro da prova de alinhamento associada à inscrição e projeto de pesquisa
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		// Somente se a nota final de todas as provas escritas for maior do que a nota mínima para aprovação
		// Somente se o projeto exigir prova oral
		return false;
	}
	
	public boolean indicaNotasAlinhamentoInicial(int idInscricao, String codigoProjetoPesquisa, String[] codigoSubcriterios, int[] notas, String justificativa, int notaFinal)
	{
		// Muda o campo justificativaNotasInicial do alinhamento da inscrição e projeto de pesquisa de acordo com o parâmetro
		// Muda o campo jsonSubcriteriosInicial com as avaliações dos critérios recebidas como parâmetro
		// Muda o campo notaFinal de acordo com o parâmetro
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		// Somente se a nota final de todas as provas escritas for maior do que a nota mínima para aprovação
		// Somente se o projeto de pesquisa não exigir prova oral ou estiver presente na prova oral
		return false;
	}
	
	public boolean indicaNotasAlinhamentoRecurso(int idInscricao, String codigoProjetoPesquisa, String[] codigoSubcriterios, int[] notas, String justificativa, int notaFinal)
	{
		// Muda o campo justificativaNotasRecurso do alinhamento da inscrição e projeto de pesquisa de acordo com o parâmetro
		// Muda o campo jsonSubcriteriosRecurso com as avaliações dos critérios recebidas como parâmetro
		// Muda o campo notaFinal de acordo com o parâmetro
		// Somente se o campo homologadoInicial estiver TRUE ou o campo homologadoRecurso estiver TRUE
		// Somente se o campo dispensadoProvaInicial estiver FALSE ou dispensadoProvaRecurso estiver FALSE
		// Somente se a nota final de todas as provas escritas for maior do que a nota mínima para aprovação
		// Somente se o projeto de pesquisa não exigir prova oral ou estiver presente na prova oral
		return false;
	}
	
	// TODO métodos de consulta (lista de homologados, lista prova A, lista prova B, ...)
	
	// TODO faz sentido ter flags de aprovação das fases de provas escritas e de alinhamento?
	
	// TODO criar script para povoar as inscrições para os nossos editais
}