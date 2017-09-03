package br.unirio.dsw.selecaoppgi.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.usuario.Usuario;
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
    private MessageSource messageSource;
    
	@Autowired
	private UsuarioDAO userDAO; 

	@Autowired
	private EditalDAO editalDAO; 

	/**
	 * Ação que redireciona o usuário para a lista de editais
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/list", method = RequestMethod.GET)
	public ModelAndView mostraPaginaLista()
	{
		return new ModelAndView("edital/list");
	}

	/**
	 * Ação que redireciona o usuário para o formulário de edição de edital
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/edit/{id}", method = RequestMethod.GET)
	public ModelAndView mostraPaginaEdicao(@PathVariable("id") int id)
	{
		ModelAndView model = new ModelAndView("edital/form");
		model.getModel().put("id", id);
		return model;
	}

	/**
	 * Ação que redireciona o usuário para o formulário de criação de edital
	 */
	@RequestMapping(value = "/edital/create", method = RequestMethod.GET)
	public ModelAndView mostraPaginaCriacao()
	{
		ModelAndView model = new ModelAndView("edital/form");
		model.getModel().put("id", -1);
		return model;
	}

	/**
	 * Ação AJAX que lista todos os editais
	 */
	@RequestMapping(value = "/edital", method = RequestMethod.GET, produces = "application/json")
	public String lista(@ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
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
	public String geraResumos()
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
	public String getEdital(@PathVariable("id") int id, Locale locale)
	{
		Edital edital = editalDAO.carregaEditalId(id, userDAO);
		
		if (edital == null)
			return ajaxError("edital.form.nao.encontrado", locale);
		
		JsonObject jsonEdital = new JsonEditalWriter().execute(edital);
		return ajaxSuccess(jsonEdital);
	}

	/**
	 * Ação AJAX que atualiza um edital
	 */
	@RequestMapping(value = "/edital/", method = RequestMethod.POST)
	public String atualiza(@RequestBody Edital edital, Locale locale)
	{
		if (edital.getNome().length() == 0)
			return ajaxError("edital.form.nome.vazio", locale);
		
		if (edital.getNome().length() > 80)
			return ajaxError("edital.form.nome.maior.80.caracteres", locale);
		
		Edital editalMesmoNome = editalDAO.carregaEditalNome(edital.getNome(), userDAO);
		
		if (editalMesmoNome != null && editalMesmoNome.getId() != edital.getId())
			return ajaxError("edital.form.nome.duplicado", locale);
		
		if (edital.getNotaMinimaAlinhamento() <= 0)
			return ajaxError("edital.form.nota.minima.menor.igual.zero", locale);
		
		if (edital.getNotaMinimaAlinhamento() >= 100)
			return ajaxError("edital.form.nota.minima.maior.igual.cem", locale);

		editalDAO.atualiza(edital);
		return ajaxSuccess();
	}
	
	private String ajaxError(String message, Locale locale)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "FAIL");
		json.addProperty("message", messageSource.getMessage(message, null, locale));
		return json.toString();
	}
	
	private String ajaxSuccess()
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		return json.toString();
	}
	
	private String ajaxSuccess(JsonObject jsonDados)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		json.add("data", jsonDados);
		return json.toString();
	}

	/**
	 * Ação AJAX que remove um edital
	 */
	@RequestMapping(value = "/edital/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Edital> remove(@PathVariable("id") int id)
	{
		Edital edital = editalDAO.carregaEditalId(id, userDAO);
		
		if (edital == null)
			return new ResponseEntity<Edital>(HttpStatus.NOT_FOUND);

		editalDAO.remove(id);
		return new ResponseEntity<Edital>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Ação AJAX que troca a senha do usuário logado
	 */
	@RequestMapping(value = "/edital/muda/{id}", method = RequestMethod.POST)
	public String mudaEditalSelecionado(@PathVariable("id") int id, Locale locale)
	{
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (usuario == null)
		{
			JsonObject root = new JsonObject();
			root.addProperty("Result", "error");
			root.addProperty("Result", messageSource.getMessage("edital.muda.edital.selecionado.erro.usuario.nao.encontrado", null, locale));
			return root.toString();			
		}

		usuario.setIdEdital(id);
        userDAO.mudaEditalSelecionado(usuario.getId(), id);
        
		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		return root.toString();
	}

//	/edital/abertura
//	/edital/inscricao/encerramento
}