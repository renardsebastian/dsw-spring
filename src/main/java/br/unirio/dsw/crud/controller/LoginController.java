package br.unirio.dsw.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.unirio.dsw.crud.model.User;
import br.unirio.dsw.crud.utils.ValidationUtils;
import br.unirio.dsw.crud.view.login.RegistrationForm;

/**
 * Controller responsável pelas ações de login
 * 
 * @author marciobarros
 */
@Controller
public class LoginController 
{
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
	public String showRegistrationFormPage(WebRequest request, Model model)
	{
        model.addAttribute("user", new RegistrationForm());
		return "login/registrationForm";
	}
	
	/**
	 * Ação que cria a conta de um novo usuário
	 */
	@RequestMapping(value = "/login/create", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") RegistrationForm form, BindingResult result, WebRequest request) 
	{
		if (form.getName().length() == 0)
			addFieldError("name", form.getName(), "login.user.registration.error.name.empty", result);
		
		if (form.getEmail().length() == 0)
			addFieldError("email", form.getName(), "login.user.registration.error.email.empty", result);
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			addFieldError("email", form.getName(), "login.user.registration.error.email.invalid", result);
		
		if (DAOFactory.getUserDAO().getUserEmail(form.getEmail()) != null)
			addFieldError("email", form.getName(), "login.user.registration.error.email.taken", result);
		
		if (!ValidationUtils.validPassword(form.getPassword()))
			addFieldError("password", form.getName(), "login.user.registration.error.password.invalid", result);
		
		if (!form.getPassword().equals(form.getRepeatPassword()))
			addFieldError("password", form.getName(), "login.user.registration.error.password.different", result);
		
        if (result.hasErrors())
            return "login/RegistrationForm";
 
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
}