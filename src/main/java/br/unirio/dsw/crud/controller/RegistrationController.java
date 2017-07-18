package br.unirio.dsw.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
 
@Controller
@SessionAttributes("user")
public class RegistrationController {
 
//    private UserService service;
//    
//    @Autowired
//    public RegistrationController(UserService service) {
//        this.service = service;
//    }
// 
//    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
//    public String showRegistrationForm(WebRequest request, Model model) {
//        Connection<?> connection = ProviderSignInUtils.getConnection(request);
// 
//        RegistrationForm registration = createRegistrationDTO(connection);
//        model.addAttribute("user", registration);
// 
//        return "RegistrationForm";
//    }
// 
//    private RegistrationForm createRegistrationDTO(Connection<?> connection) {
//        RegistrationForm dto = new RegistrationForm();
// 
//        if (connection != null) {
//            UserProfile socialMediaProfile = connection.fetchUserProfile();
//            dto.setEmail(socialMediaProfile.getEmail());
//            dto.setFirstName(socialMediaProfile.getFirstName());
//            dto.setLastName(socialMediaProfile.getLastName());
// 
//            ConnectionKey providerKey = connection.getKey();
//            dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
//        }
// 
//        return dto;
//    }
//    
//    @RequestMapping(value ="/user/register", method = RequestMethod.POST)
//    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
//                                      BindingResult result,
//                                      WebRequest request) throws DuplicateEmailException {
//        if (result.hasErrors()) {
//            return "user/registrationForm";
//        }
// 
//        User registered = createUserAccount(userAccountData, result);
// 
//        if (registered == null) {
//            return "user/registrationForm";
//        }
//        SecurityUtil.logInUser(registered);
//        ProviderSignInUtils.handlePostSignUp(registered.getEmail(), request);
// 
//        return "redirect:/";
//    }
// 
//    private User createUserAccount(RegistrationForm userAccountData, BindingResult result) {
//        User registered = null;
// 
//        try {
//            registered = service.registerNewUserAccount(userAccountData);
//        }
//        catch (DuplicateEmailException ex) {
//            addFieldError(
//                    "user",
//                    "email",
//                    userAccountData.getEmail(),
//                    "NotExist.user.email",
//                    result);
//        }
// 
//        return registered;
//    }
// 
//    private void addFieldError(String objectName, String fieldName, String fieldValue,  String errorCode, BindingResult result) {
//        FieldError error = new FieldError(
//                objectName,
//                fieldName,
//                fieldValue,
//                false,
//                new String[]{errorCode},
//                new Object[]{},
//                errorCode
//        );
// 
//        result.addError(error);
//    }    
}