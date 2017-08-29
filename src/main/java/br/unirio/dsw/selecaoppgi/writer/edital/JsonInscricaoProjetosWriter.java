package br.unirio.dsw.selecaoppgi.writer.edital;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoProjetoPesquisa;

/**
 * Classe que publica os dados de uma inscrição em projetos de um edital em formato JSON
 * 
 * @author Marcio Barros
 */
public class JsonInscricaoProjetosWriter
{
	/**
	 * Gera a representação JSON
	 */
	public JsonArray execute(InscricaoEdital inscricao)
	{
		JsonArray jsonProjetos = new JsonArray();
		
		for (InscricaoProjetoPesquisa inscricaoProjeto : inscricao.getInscricoesProjetoPesquisa())
		{
			JsonObject jsonProjeto = new JsonObject();
			jsonProjeto.addProperty("codigo", inscricaoProjeto.getProjetoPesquisa().getCodigo());
			jsonProjeto.addProperty("intencoes", inscricaoProjeto.getIntencoes());
			jsonProjetos.add(jsonProjeto);
		}
		
		return jsonProjetos;
	}
}