package br.unirio.dsw.selecaoppgi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.service.dao.EditalDAO;
import br.unirio.dsw.selecaoppgi.service.dao.InscricaoDAO;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;
import br.unirio.dsw.selecaoppgi.service.relatorio.GeradorRelatorio;
import br.unirio.dsw.selecaoppgi.utils.JsonUtils;

@Controller
public class RelatorioController
{
	@Autowired
	private UsuarioDAO userDAO; 

	@Autowired
	private EditalDAO editalDAO;
	
	@Autowired
	private InscricaoDAO inscricaoDAO;
	
	/**
	 * Ação AJAX que gera o relatório de homologação original e redireciona o usuário para o link de download
	 */	
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/relatorio/edital/{id}/homologacao/original/", method = RequestMethod.GET, produces = "application/json")
	public String geraRelatorioHomologacaoOriginal(@PathVariable("id") int id)
	{
		List<String> listaAprovados = new ArrayList<String>();
		List<String> listaReprovados = new ArrayList<String>();
		List<String> listaJustificativas = new ArrayList<String>();		
		
		GeradorRelatorio geradorRelatorio = new GeradorRelatorio();
		Edital edital = editalDAO.carregaEditalId(id, userDAO);		
		List<InscricaoEdital> listaInscricoes = new InscricaoDAO().carregaInscricoesEdital(edital);		
		for(InscricaoEdital inscrEdital : listaInscricoes){
			if(inscrEdital.getHomologadoOriginal()){
				listaAprovados.add(inscrEdital.getNomeCandidato());
			}else{
				listaReprovados.add(inscrEdital.getNomeCandidato());
				//Se justificativa == null, define texto padrão
	            if(inscrEdital.getJustificativaHomologacaoOriginal() == null) 
	            	inscrEdital.setJustificativaHomologacaoOriginal("Sem justificativa definida");
				listaJustificativas.add(inscrEdital.getJustificativaHomologacaoOriginal());
			}
		}
		String nomeArquivo = geradorRelatorio.relatorioInscricoesHomologadas(listaAprovados, listaReprovados, listaJustificativas);		
		JsonObject jsonEdital = new JsonObject();
		jsonEdital.addProperty("nomeArquivo", nomeArquivo);
		System.out.println(jsonEdital);
		return JsonUtils.ajaxSuccess(jsonEdital);
	}	
	
	/**
	 * Ação AJAX que gera o relatório de homologação após recurso e redireciona o usuário para o link de download
	 */
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/relatorio/edital/{id}/homologacao/recurso", method = RequestMethod.GET, produces = "application/json")
	public String geraRelatorioHomologacaoRecurso(@PathVariable("id") int id)
	{		
		List<String> listaAprovados = new ArrayList<String>();
		List<String> listaReprovados = new ArrayList<String>();
		List<String> listaJustificativas = new ArrayList<String>();		
		
		GeradorRelatorio geradorRelatorio = new GeradorRelatorio();
		Edital edital = editalDAO.carregaEditalId(id, userDAO);		
		List<InscricaoEdital> listaInscricoes = new InscricaoDAO().carregaInscricoesEdital(edital);		
		for(InscricaoEdital inscrEdital : listaInscricoes){
			if(!inscrEdital.getHomologadoOriginal() && inscrEdital.getHomologadoRecurso()){
				listaAprovados.add(inscrEdital.getNomeCandidato());
			}else if(!inscrEdital.getHomologadoOriginal() && !inscrEdital.getHomologadoRecurso()){
				listaReprovados.add(inscrEdital.getNomeCandidato());
				//Se justificativa == null, define texto padrão
				if(inscrEdital.getJustificativaHomologacaoRecurso() == null) inscrEdital.setJustificativaHomologacaoRecurso("Sem justificativa definida");
				listaJustificativas.add(inscrEdital.getJustificativaHomologacaoRecurso());
			}
		}
		System.out.println(listaAprovados);
		System.out.println(listaReprovados);
		String nomeArquivo = geradorRelatorio.relatorioInscricoesHomologadasAposRecurso(listaAprovados, listaReprovados, listaJustificativas);		
		JsonObject jsonEdital = new JsonObject();
		jsonEdital.addProperty("nomeArquivo", nomeArquivo);
		System.out.println(jsonEdital);
		return JsonUtils.ajaxSuccess(jsonEdital);
	}		
	
	/**
	 * Servlet para downloads dos relatórios disponíveis em pdf
	 */
	@RestController
	@Secured("ROLE_ADMIN")
	@RequestMapping("/download")
	public class DownloadRelatorioRestController {
		@Autowired
		ServletContext context;
		
		@RequestMapping(value = "/relatorio/{fileName:.+}", method = RequestMethod.GET, produces = "application/pdf")
		public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName) throws IOException {
			System.out.println("Calling Download:- " + fileName);
			ClassPathResource pdfFile = new ClassPathResource("downloads/" + fileName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + fileName);
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			
			headers.setContentLength(pdfFile.contentLength());
			ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
					new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
			return response;			
		}
	}
	
//	/relatorio/homologacao/homologacao/recurso
//	/relatorio/homologacao/dispensa/original
//	/relatorio/homologacao/dispensa/recurso
//	/relatorio/escritas/presenca
//	/relatorio/escritas/notas/original
//	/relatorio/escritas/notas/recurso
//	/relatorio/escritas/pendencias
//	/relatorio/alinhamento/presenca
//	/relatorio/alinhamento/notas/original
//	/relatorio/alinhamento/notas/recurso
//	/relatorio/alinhamento/pendencias
//	/relatorio/aprovacao
}