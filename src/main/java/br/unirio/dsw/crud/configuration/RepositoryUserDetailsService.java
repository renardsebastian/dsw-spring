package br.unirio.dsw.crud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.unirio.dsw.crud.model.User;
import br.unirio.dsw.crud.service.UserService;
 
public class RepositoryUserDetailsService implements UserDetailsService {
 
    private UserService service;
 
    @Autowired
    public RepositoryUserDetailsService(UserService service) {
        this.service = service;
    }
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findByEmail(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
// 
//        User principal = new User();
//                .firstName(user.getFirstName())
//                .id(user.getId())
//                .lastName(user.getLastName())
//                .password(user.getPassword())
//                .role(user.getRole())
//                .socialSignInProvider(user.getSignInProvider())
//                .username(user.getEmail())
//                .build();
// 
        return user;
    }
}
