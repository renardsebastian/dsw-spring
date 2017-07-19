package br.unirio.dsw.crud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.unirio.dsw.crud.dao.DAOFactory;
import br.unirio.dsw.crud.dao.UserDAO.UserOrderingCriteria;
import br.unirio.dsw.crud.dao.UserDAO.UserOrderingField;
import br.unirio.dsw.crud.model.User;

/**
 * Classe de acesso aos usuários registrados no sistema
 * 
 * @author marciobarros
 */
@Service("userService")
public class UserService
{
	/**
	 * Retorna todos os usuários
	 */
	public List<User> findAllUsers()
	{
		return DAOFactory.getUserDAO().list(0, 10000, UserOrderingField.Name, UserOrderingCriteria.ASC, "", "");
	}

	/**
	 * Retorna um usuário, dado seu ID
	 */
	public User findById(int id)
	{
		return DAOFactory.getUserDAO().getUserId(id);
	}

	/**
	 * Retorna um usuário, dado seu email
	 */
	public User findByEmail(String email)
	{
		return DAOFactory.getUserDAO().getUserEmail(email);
	}

	/**
	 * Salva os dados de um novo usuário
	 */
	public void saveUser(User user)
	{
		DAOFactory.getUserDAO().createUser(user);
	}

	/**
	 * Verifica se existe um usuário com um email
	 */
	public boolean isUserExist(User user)
	{
		return findByEmail(user.getUsername()) != null;
	}
}