import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;

public class CriterioAlinhamentoTest
{
	@Test
	public void testToJson()
	{
		CriterioAlinhamento criterio = new CriterioAlinhamento();
		criterio.setNome("Nome da prova");
		criterio.setPesoComProvaOral(70);
		criterio.setPesoSemProvaOral(80);
		criterio.setPertenceProvaOral(true);

		SubcriterioAlinhamento subcriterio1 = new SubcriterioAlinhamento();
		subcriterio1.setNome("Subcritério 1");
		subcriterio1.setDescricao("Descrição subcritério 1");
		subcriterio1.setPeso(30);
		criterio.adicionaSubcriterio(subcriterio1);

		SubcriterioAlinhamento subcriterio2 = new SubcriterioAlinhamento();
		subcriterio2.setNome("Subcritério 2");
		subcriterio2.setDescricao("Descrição subcritério 2");
		subcriterio2.setPeso(70);
		criterio.adicionaSubcriterio(subcriterio2);
		
		assertEquals(criterio.toJson().toString(), "{\"nome\":\"Nome da prova\",\"pesoComProvaOral\":70,\"pesoSemProvaOral\":80,\"pertenceProvaOral\":true,\"subcriterios\":[{\"nome\":\"Subcritério 1\",\"descricao\":\"Descrição subcritério 1\",\"peso\":30},{\"nome\":\"Subcritério 2\",\"descricao\":\"Descrição subcritério 2\",\"peso\":70}]}");
	}

	@Test
	public void testFromJson()
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse("{\"nome\":\"Nome da prova\",\"pesoComProvaOral\":70,\"pesoSemProvaOral\":80,\"pertenceProvaOral\":true,\"subcriterios\":[{\"nome\":\"Subcritério 1\",\"descricao\":\"Descrição subcritério 1\",\"peso\":30},{\"nome\":\"Subcritério 2\",\"descricao\":\"Descrição subcritério 2\",\"peso\":70}]}").getAsJsonObject();

		CriterioAlinhamento criterios = new CriterioAlinhamento();
		criterios.fromJson(json);

		assertEquals(criterios.getNome(), "Nome da prova");
		assertEquals(criterios.getPesoComProvaOral(), 70);
		assertEquals(criterios.getPesoSemProvaOral(), 80);
		assertEquals(criterios.isPertenceProvaOral(), true);
		assertEquals(criterios.contaSubcriterios(), 2);
		
		assertEquals(criterios.pegaSubcriterioIndice(0).getNome(), "Subcritério 1");
		assertEquals(criterios.pegaSubcriterioIndice(0).getDescricao(), "Descrição subcritério 1");
		assertEquals(criterios.pegaSubcriterioIndice(0).getPeso(), 30);
		
		assertEquals(criterios.pegaSubcriterioIndice(1).getNome(), "Subcritério 2");
		assertEquals(criterios.pegaSubcriterioIndice(1).getDescricao(), "Descrição subcritério 2");
		assertEquals(criterios.pegaSubcriterioIndice(1).getPeso(), 70);
	}
}