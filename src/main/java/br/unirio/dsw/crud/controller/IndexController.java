package br.unirio.dsw.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller que remete o usuário para a página inicial da aplicação
 * 
 * @author marciobarros
 */
@Controller
@RequestMapping("/")
public class IndexController
{
	@RequestMapping(method = RequestMethod.GET)
	public String getIndexPage()
	{
		return "homepage/Index";
	}
}