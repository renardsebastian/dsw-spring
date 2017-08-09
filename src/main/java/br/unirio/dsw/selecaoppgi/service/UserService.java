package br.unirio.dsw.selecaoppgi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unirio.dsw.selecaoppgi.dao.UserDAO;
import br.unirio.dsw.selecaoppgi.dao.UserDAO.UserOrderingCriteria;
import br.unirio.dsw.selecaoppgi.dao.UserDAO.UserOrderingField;
import br.unirio.dsw.selecaoppgi.model.User;

/**
 * Classe de acesso aos usuários registrados no sistema
 * 
 * @author marciobarros
 */
@Service("userService")
public class UserService
{
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * Retorna todos os usuários
	 */
	public List<User> findAllUsers()
	{
		return userDAO.list(0, 10000, UserOrderingField.Name, UserOrderingCriteria.ASC, "", "");
	}

	/**
	 * Retorna um usuário, dado seu ID
	 */
	public User findById(int id)
	{
		return userDAO.getUserId(id);
	}

	/**
	 * Retorna um usuário, dado seu email
	 */
	public User findByEmail(String email)
	{
		return userDAO.getUserEmail(email);
	}

	/**
	 * Salva os dados de um novo usuário
	 */
	public void saveUser(User user)
	{
		userDAO.createUser(user);
	}

	/**
	 * Verifica se existe um usuário com um email
	 */
	public boolean isUserExist(User user)
	{
		return findByEmail(user.getUsername()) != null;
	}
}