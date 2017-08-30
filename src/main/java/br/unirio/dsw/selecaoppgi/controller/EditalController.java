package br.unirio.dsw.selecaoppgi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.service.dao.EditalDAO;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;
import br.unirio.dsw.selecaoppgi.writer.edital.JsonEditalWriter;

/**
 * Controller responsável pelo gerenciamento de editais
 * 
 * @author marciobarros
 */
@RestController
public class EditalController
{
	@Autowired
	private UsuarioDAO userDAO; 

	@Autowired
	private EditalDAO editalDAO; 

	/**
	 * Ação que redireciona o usuário para a lista de editais
	 */
	@RequestMapping(value = "/edital/list", method = RequestMethod.GET)
	public ModelAndView getPaginaLista()
	{
		return new ModelAndView("edital/list");
	}

	/**
	 * Ação que redireciona o usuário para o formulário de edição de edital
	 */
	@RequestMapping(value = "/edital/edit/{id}", method = RequestMethod.GET)
	public ModelAndView getPaginaEdicao(@PathVariable("id") int id)
	{
		ModelAndView model = new ModelAndView("edital/form");
		model.getModel().put("id", id);
		return model;
	}

	/**
	 * Ação que redireciona o usuário para o formulário de criação de edital
	 */
	@RequestMapping(value = "/edital/create", method = RequestMethod.GET)
	public ModelAndView getPaginaCriacao()
	{
		ModelAndView model = new ModelAndView("edital/form");
		model.getModel().put("id", -1);
		return model;
	}

	/**
	 * Ação AJAX que lista todos os editais
	 */
	@RequestMapping(value = "/edital", method = RequestMethod.GET, produces = "application/json")
	public String list(@ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
	{
		List<Edital> editais = editalDAO.lista(pagina, tamanho, filtroNome);
		int total = editalDAO.conta(filtroNome);
		
		JsonArray jsonEditais = new JsonArray();
		JsonEditalWriter writer = new JsonEditalWriter();
		
		for (Edital edital : editais)
			jsonEditais.add(writer.execute(edital));
		
		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		root.addProperty("TotalRecordCount", total);
		root.add("Records", jsonEditais);
		return root.toString();
	}

	/**
	 * Ação AJAX que lista todos os editais
	 */
	@RequestMapping(value = "/edital/summary", method = RequestMethod.GET, produces = "application/json")
	public String summary()
	{
		List<Edital> editais = editalDAO.lista(0, 100000, "");
		JsonArray jsonEditais = new JsonArray();
		
		for (Edital edital : editais)
		{
			JsonObject jsonEdital = new JsonObject();
			jsonEdital.addProperty("id", edital.getId());
			jsonEdital.addProperty("nome", edital.getNome());
			jsonEditais.add(jsonEdital);
		}
		
		return jsonEditais.toString();
	}

	/**
	 * Ação AJAX que recupera um edital, dado seu ID
	 */
	@RequestMapping(value = "/edital/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Edital> getEdital(@PathVariable("id") int id)
	{
		Edital edital = editalDAO.getEditalId(id, userDAO);
		
		if (edital == null)
			return new ResponseEntity<Edital>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Edital>(edital, HttpStatus.OK);
	}

	/**
	 * Ação que atualiza um edital
	 */
	@RequestMapping(value = "/edital/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Edital edital, UriComponentsBuilder ucBuilder)
	{
		// TODO Regras de negocio
//		if (userDAO.getUserEmail(editalDAO.getUsername()) != null)
//			return new ResponseEntity<Void>(HttpStatus.CONFLICT);

		editalDAO.atualiza(edital);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/edital/{id}").buildAndExpand(edital.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Ação que remove um edital
	 */
	@RequestMapping(value = "/edital/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Edital> delete(@PathVariable("id") int id)
	{
		Edital edital = editalDAO.getEditalId(id, userDAO);
		
		if (edital == null)
			return new ResponseEntity<Edital>(HttpStatus.NOT_FOUND);

		editalDAO.remove(id);
		return new ResponseEntity<Edital>(HttpStatus.NO_CONTENT);
	}
}