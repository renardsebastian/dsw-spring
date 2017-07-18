package br.unirio.dsw.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import br.unirio.dsw.crud.model.User;
import br.unirio.dsw.crud.view.login.RegistrationForm;
 
@Controller
public class LoginController {
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() 
    {
        return "login/Login";
    }

	@RequestMapping(value = "/login/register", method = RequestMethod.GET)
	public String showRegistrationFormPage(WebRequest request, Model model)
	{
        model.addAttribute("user", new RegistrationForm());
		return "login/RegistrationForm";
	}
	 
	@RequestMapping(value = "/login/create", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") RegistrationForm form, BindingResult result, WebRequest request) 
	{
		if (form.getName().length() == 0)
			addFieldError("name", form.getName(), "error.name.empty", result);
		
        if (result.hasErrors())
            return "login/RegistrationForm";
 
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        // salva o caboclo
 
//        if (registered == null)
//            return "user/registrationForm";

//        SecurityUtils.logInUser(registered);
//        ProviderSignInUtils.handlePostSignUp(user.getEmail(), request);
 
        return "redirect:/";
    }
 
    private void addFieldError(String fieldName, String fieldValue, String errorCode, BindingResult result) 
    {
        FieldError error = new FieldError("user", fieldName, fieldValue, false, new String[]{errorCode}, new Object[]{}, errorCode);
        result.addError(error);
    }
}