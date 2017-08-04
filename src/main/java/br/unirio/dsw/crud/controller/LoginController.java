package br.unirio.dsw.crud.controller;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import br.unirio.dsw.crud.dao.DAOFactory;
import br.unirio.dsw.crud.dao.UserDAO;
import br.unirio.dsw.crud.model.User;
import br.unirio.dsw.crud.service.EmailService;
import br.unirio.dsw.crud.utils.Configuration;
import br.unirio.dsw.crud.utils.CryptoUtils;
import br.unirio.dsw.crud.utils.ValidationUtils;
import br.unirio.dsw.crud.view.login.ForgotPasswordForm;
import br.unirio.dsw.crud.view.login.RegistrationForm;

/**
 * Controller responsável pelas ações de login
 * 
 * @author marciobarros
 */
@Controller
public class LoginController 
{
    @Autowired
    private MessageSource messageSource;
    
	private PasswordEncoder passwordEncoder;
    
    /**
     * Inicializa o controlador
     */
    @Autowired
    public LoginController(PasswordEncoder passwordEncoder) 
    {
        this.passwordEncoder = passwordEncoder;
    }
 
	/**
	 * Ação que redireciona o usuário para a página inicial da aplicação
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndexPage()
	{
		return "homepage/Index";
	}

	/**
	 * Ação que redireciona o usuário para a tela de login
	 */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() 
    {
        return "login/login";
    }

    /**
     * Ação que redireciona o usuário para a tela de criação de conta
     */
	@RequestMapping(value = "/login/register", method = RequestMethod.GET)
	public String showRegistrationPage(WebRequest request, Model model)
	{
        model.addAttribute("user", new RegistrationForm());
		return "login/create";
	}
	
	/**
	 * Ação que cria a conta de um novo usuário
	 */
	@RequestMapping(value = "/login/create", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") RegistrationForm form, BindingResult result, WebRequest request) 
	{
		if (form.getName().length() == 0)
			addFieldError("name", form.getName(), "login.new.account.error.name.empty", result);
		
		if (form.getEmail().length() == 0)
			addFieldError("email", form.getEmail(), "login.new.account.error.email.empty", result);
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			addFieldError("email", form.getEmail(), "login.new.account.error.email.invalid", result);
		
		if (DAOFactory.getUserDAO().getUserEmail(form.getEmail()) != null)
			addFieldError("email", form.getEmail(), "login.new.account.error.email.taken", result);
		
		if (!ValidationUtils.validPassword(form.getPassword()))
			addFieldError("password", form.getPassword(), "login.new.account.error.password.invalid", result);
		
		if (!form.getPassword().equals(form.getRepeatPassword()))
			addFieldError("password", form.getPassword(), "login.new.account.error.password.different", result);
		
        if (result.hasErrors())
            return "login/create";
 
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        User user = new User(form.getName(), form.getEmail(), encodedPassword);
        DAOFactory.getUserDAO().createUser(user);
 
//        SecurityUtils.logInUser(registered);
//        ProviderSignInUtils.handlePostSignUp(user.getEmail(), request);
        return "redirect:/";
    }
    
	/**
	 * Registra um erro associado a um campo de um formulário
	 */
    private void addFieldError(String fieldName, String fieldValue, String errorCode, BindingResult result) 
    {
        FieldError error = new FieldError("user", fieldName, fieldValue, false, new String[]{errorCode}, new Object[]{}, errorCode);
        result.addError(error);
    }

    /**
     * Ação que redireciona o usuário para a tela de esquecimento de senha
     */
	@RequestMapping(value = "/login/forgot", method = RequestMethod.GET)
	public String showForgotPasswordPage(WebRequest request, Model model)
	{
        model.addAttribute("form", new ForgotPasswordForm());
		return "login/forgot";
	}

	/**
	 * Ação que envia um token para troca de senha
	 */
	@RequestMapping(value = "/login/sendToken", method = RequestMethod.POST)
	private String sendToken(@ModelAttribute("form") RegistrationForm form, BindingResult result, WebRequest request, Locale locale)
	{
		if (form.getEmail().length() == 0)
			addFieldError("email", form.getEmail(), "login.forgot.password.error.email.empty", result);
		
        if (result.hasErrors())
            return "login/forgot";
		
		User user = new UserDAO().getUserEmail(form.getEmail());

		if (user != null)
		{
			String token = geraTokenTrocaSenha();
			new UserDAO().saveLoginToken(user.getId(), user.getLoginToken());
			
			String url = Configuration.getHostname() + "/login/resetPassword.do?token=" + token + "&email=" + user.getUsername();		
			String title = messageSource.getMessage("login.forgot.password.email.inicializacao.senha.titulo", null, locale);
			String contents = messageSource.getMessage("login.forgot.password.email.inicializacao.senha.corpo", new String[] { url }, locale);
			EmailService.getInstance().sendToUser(user.getName(), user.getUsername(), title, contents);
		}
		
        return "redirect:/login";
	}
	
	/**
	 * Gera um token para troca de senha
	 */
	private String geraTokenTrocaSenha()
	{
		StringBuilder sb = new StringBuilder();
		Random r = CryptoUtils.createSecureRandom();
		
		for (int i = 0; i < 256; i++)
		{
			char c = (char) ('A' + r.nextInt(26));
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	/**
	 * Ação que prepara o formulário de reset de senha
	 */
//	@RequestMapping(value = "/login/resetPassword", method = RequestMethod.GET)
//	public String showResetPassword(@ModelAttribute("email") String email, @ModelAttribute("token") String token, BindingResult result, WebRequest request)
//	{
//		String token = getParameter("token", "");
//		checkNonEmpty(token, translate("login.reset.erros.tokennaoencontrado"));
//		
//		String email = getParameter("email", "");
//		checkNonEmpty(email, translate("login.reset.erros.emailnaoencontrado"));
//
//		Participante participante = PlataformaDAOFactory.getParticipanteDAO().getParticipanteEmail(email);
//		check(participante != null, translate("login.reset.erros.participantenaoencontrado"));
//
//		boolean valido = PlataformaDAOFactory.getParticipanteDAO().verificaTokenTrocaSenha(participante.getId(), token, 72);
//		check(valido, translate("login.reset.erros.tokeninvalido"));
//		
//		setAttribute("token", token);
//		setAttribute("email", email);
//		return SUCCESS;
//	}
	
//	/**
//	 * Executa uma troca de senha baseada em reinicialização
//	 */
//	@DisableUserVerification
//	@Success(type=ResultType.Redirect, value="/login/homepage.do")
//	@Error("/jsp/plataforma/registro/resetSenha.jsp")
//	public String executaResetSenha() throws ActionException
//	{
//		String token = getParameter("token", "");
//		checkNonEmpty(token, translate("login.reset.erros.tokennaoencontrado"));
//		
//		String email = getParameter("email", "");
//		checkNonEmpty(email, translate("login.reset.erros.emailnaoencontrado"));
//
//		setAttribute("token", token);
//		setAttribute("email", email);
//
//		Participante participante = PlataformaDAOFactory.getParticipanteDAO().getParticipanteEmail(email);
//		check(participante != null, translate("login.reset.erros.participantenaoencontrado"));
//
//		boolean valido = PlataformaDAOFactory.getParticipanteDAO().verificaTokenTrocaSenha(participante.getId(), token, 72);
//		check(valido, translate("login.reset.erros.tokeninvalido"));
//		
//		String novaSenha = getParameter("newpassword");
//		check(novaSenha != null, translate("login.erros.novasenha.vazia"));
//		check(participante.senhaAceitavel(novaSenha), translate("login.erros.novasenha.fraca"));
//
//		String novaSenha2 = getParameter("newpassword2");
//		check(novaSenha2 != null, translate("login.erros.novasenha.repetevazia"));
//		check(novaSenha.compareTo(novaSenha2) == 0, translate("login.erros.novasenha.diferente"));
//
//		check(PlataformaDAOFactory.getParticipanteDAO().atualizaSenha(participante.getId(), participante.getId(), novaSenha), translate("login.erros.novasenha.erro"));
//		
//		return addRedirectNotice("senha.trocada");
//	}
}