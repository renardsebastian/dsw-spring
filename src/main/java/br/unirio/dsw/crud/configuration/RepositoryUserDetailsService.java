package br.unirio.dsw.crud.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.unirio.dsw.crud.dao.DAOFactory;
import br.unirio.dsw.crud.model.User;
 
public class RepositoryUserDetailsService implements UserDetailsService 
{
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = DAOFactory.getUserDAO().getUserEmail(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return user;
    }
}
