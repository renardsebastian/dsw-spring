import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;

public class ProvaEscritaTest
{
	@Test
	public void testToJson()
	{
		ProvaEscrita prova = new ProvaEscrita();
		prova.setSigla("ABC");
		prova.setNome("Nome da prova");
		prova.setDispensavel(true);
		prova.setNotaMinimaAprovacao(70);
		prova.adicionaQuestao(30);
		prova.adicionaQuestao(30);
		prova.adicionaQuestao(40);
		assertEquals(prova.toJson().toString(), "{\"sigla\":\"ABC\",\"nome\":\"Nome da prova\",\"dispensavel\":true,\"notaMinima\":70,\"questoes\":[30,30,40]}");
	}

	@Test
	public void testFromJson()
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse("{\"sigla\":\"ABC\",\"nome\":\"Nome da prova\",\"dispensavel\":true,\"notaMinima\":70,\"questoes\":[30,30,40]}").getAsJsonObject();

		ProvaEscrita prova = new ProvaEscrita();
		prova.fromJson(json);

		assertEquals(prova.getSigla(), "ABC");
		assertEquals(prova.getNome(), "Nome da prova");
		assertEquals(prova.isDispensavel(), true);
		assertEquals(prova.getNotaMinimaAprovacao(), 70);
		assertEquals(prova.contaQuestoes(), 3);
		assertEquals(prova.pegaPesoQuestaoIndice(0), 30);
		assertEquals(prova.pegaPesoQuestaoIndice(1), 30);
		assertEquals(prova.pegaPesoQuestaoIndice(2), 40);
	}
}