package br.unirio.dsw.selecaoppgi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.reader.edital.JsonEditalReader;
import br.unirio.dsw.selecaoppgi.utils.JsonUtils;
import br.unirio.dsw.selecaoppgi.writer.edital.JsonEditalWriter;

public class TestEditalJson
{
	@Test
	public void testBasico()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		JsonEditalWriter writer = new JsonEditalWriter();
		JsonObject jsonOriginal = writer.execute(edital);
		
		Edital editalClone = new Edital();
		JsonEditalReader reader = new JsonEditalReader();
		reader.execute(jsonOriginal, editalClone, null);
		
		JsonObject jsonClonado = writer.execute(editalClone);
		assertTrue(JsonUtils.compare(jsonOriginal, jsonClonado));
	}
}