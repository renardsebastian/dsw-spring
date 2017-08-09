package br.unirio.dsw.selecaoppgi.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.unirio.dsw.selecaoppgi.dao.UserDAO;
import br.unirio.dsw.selecaoppgi.model.User;
 
public class RepositoryUserDetailsService implements UserDetailsService 
{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        User user = new UserDAO().getUserEmail(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return user;
    }
}
