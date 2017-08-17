package br.unirio.dsw.selecaoppgi.controller;

import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import br.unirio.dsw.selecaoppgi.configuration.ApplicationConfiguration;
import br.unirio.dsw.selecaoppgi.model.usuario.User;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;
import br.unirio.dsw.selecaoppgi.service.email.EmailService;
import br.unirio.dsw.selecaoppgi.utils.CryptoUtils;
import br.unirio.dsw.selecaoppgi.utils.ValidationUtils;
import br.unirio.dsw.selecaoppgi.view.login.ForgotPasswordForm;
import br.unirio.dsw.selecaoppgi.view.login.RegistrationForm;
import br.unirio.dsw.selecaoppgi.view.login.ResetPasswordForm;

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
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
    @Autowired
	private UserDAO userDAO;
    
    @Autowired
	private EmailService emailService;
 
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
    public ModelAndView showLoginPage(@RequestParam(value = "error", required = false) String error, HttpServletRequest request) 
    {
		ModelAndView model = new ModelAndView();
	
		if (error != null)
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

		model.setViewName("login/login");
		return model;
    }

    /**
     * Retorna a última mensagem de erro do processo de login
     */
	private String getErrorMessage(HttpServletRequest request, String key){

		Exception exception = (Exception) request.getSession().getAttribute(key);

		if (exception instanceof BadCredentialsException) 
			return "login.login.message.invalid.credentials";
		
		if (exception instanceof LockedException) 
			return "login.login.message.locked.account";

		return "login.login.message.invalid.credentials";
	}
	
    /**
     * Ação que redireciona o usuário para a tela de criação de conta
     */
	@RequestMapping(value = "/login/create", method = RequestMethod.GET)
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
		
		if (userDAO.getUserEmail(form.getEmail()) != null)
			addFieldError("email", form.getEmail(), "login.new.account.error.email.taken", result);
		
		if (!ValidationUtils.validPassword(form.getPassword()))
			addFieldError("password", form.getPassword(), "login.new.account.error.password.invalid", result);
		
		if (!form.getPassword().equals(form.getRepeatPassword()))
			addFieldError("password", form.getPassword(), "login.new.account.error.password.different", result);
		
        if (result.hasErrors())
            return "login/create";
 
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        User user = new User(form.getName(), form.getEmail(), encodedPassword, false);
        userDAO.createUser(user);
 
//        SecurityUtils.logInUser(registered);
//        ProviderSignInUtils.handlePostSignUp(user.getEmail(), request);
        return "redirect:/login?message=login.new.account.success.created";
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
	@RequestMapping(value = "/login/forgot", method = RequestMethod.POST)
	private String sendToken(@ModelAttribute("form") RegistrationForm form, BindingResult result, WebRequest request, Locale locale)
	{
		if (form.getEmail().length() == 0)
			addFieldError("email", form.getEmail(), "login.forgot.password.error.email.empty", result);
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			addFieldError("email", form.getEmail(), "login.forgot.password.error.email.invalid", result);

        if (result.hasErrors())
            return "login/forgot";
		
		User user = userDAO.getUserEmail(form.getEmail());

		if (user != null)
		{
			String token = geraTokenTrocaSenha();
			userDAO.saveLoginToken(user.getId(), token);
			
			String url = ApplicationConfiguration.getHostname() + "/login/reset.do?token=" + token + "&email=" + user.getUsername();		
			String title = messageSource.getMessage("login.forgot.password.email.inicializacao.senha.titulo", null, locale);
			String contents = messageSource.getMessage("login.forgot.password.email.inicializacao.senha.corpo", new String[] { url }, locale);
			emailService.sendToUser(user.getName(), user.getUsername(), title, contents);
		}
		
        return "redirect:/login?message=login.forgot.password.success.email.sent";
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
	@RequestMapping(value = "/login/reset", method = RequestMethod.GET)
	public String showResetPasswordPage(@ModelAttribute("email") String email, @ModelAttribute("token") String token, WebRequest request, Model model)
	{
		ResetPasswordForm form = new ResetPasswordForm();
		form.setEmail(email);
		form.setToken(token);
        model.addAttribute("form", form);
		return "login/reset";
	}
	
	/**
	 * Ação que troca a senha baseada em reinicialização
	 */
	@RequestMapping(value = "/login/reset", method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute("form") ResetPasswordForm form, BindingResult result, WebRequest request, Locale locale)
	{
		if (form.getEmail().length() == 0)
			addFieldError("newPassword", form.getEmail(), "login.reset.password.error.email.empty", result);
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			addFieldError("newPassword", form.getEmail(), "login.reset.password.error.email.invalid", result);
		
		if (form.getToken().length() == 0)
			addFieldError("newPassword", form.getToken(), "login.reset.password.error.token.empty", result);
		
		User user = userDAO.getUserEmail(form.getEmail());

		if (user == null)
			addFieldError("newPassword", form.getEmail(), "login.reset.password.error.email.unrecognized", result);
		
		if (!userDAO.isValidLoginToken(form.getEmail(), form.getToken(), 72))
			addFieldError("newPassword", form.getToken(), "login.reset.password.error.token.invalid", result);
		
		if (!ValidationUtils.validPassword(form.getNewPassword()))
			addFieldError("newPassword", form.getNewPassword(), "login.reset.password.error.password.invalid", result);
		
		if (!form.getNewPassword().equals(form.getRepeatNewPassword()))
			addFieldError("repeatNewPassword", form.getNewPassword(), "login.reset.password.error.password.different", result);
		
        if (result.hasErrors())
            return "login/reset";
 
        String encodedPassword = passwordEncoder.encode(form.getNewPassword());
        userDAO.updatePassword(user.getId(), encodedPassword);
        return "redirect:/login?message=login.reset.password.success.created";
	}
}