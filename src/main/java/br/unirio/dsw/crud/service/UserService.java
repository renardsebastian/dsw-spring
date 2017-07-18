package br.unirio.dsw.crud.service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.unirio.dsw.crud.model.User;

/**
 * Classe de acesso aos usuários registrados no sistema
 * 
 * @author marciobarros
 */
@Service("userService")
public class UserService
{
	private static final AtomicLong counter = new AtomicLong();

	private static List<User> users;

	/**
	 * Inicialização estática
	 */
//	static
//	{
//		users = new ArrayList<User>();
//		users.add(new User(counter.incrementAndGet(), "Sam", "NY", "sam@abc.com"));
//		users.add(new User(counter.incrementAndGet(), "Tomy", "ALBAMA", "tomy@abc.com"));
//		users.add(new User(counter.incrementAndGet(), "Kelly", "NEBRASKA", "kelly@abc.com"));
//	}

	/**
	 * Retorna todos os usuários
	 */
	public List<User> findAllUsers()
	{
		return users;
	}

	/**
	 * Retorna um usuário, dado seu ID
	 */
	public User findById(long id)
	{
		for (User user : users)
			if (user.getId() == id)
				return user;
		
		return null;
	}

	/**
	 * Retorna um usuário, dado seu email
	 */
	public User findByEmail(String email)
	{
		for (User user : users)
			if (user.getEmail().equalsIgnoreCase(email))
				return user;

		return null;
	}

	/**
	 * Salva os dados de um novo usuário
	 */
	public void saveUser(User user)
	{
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	/**
	 * Atualiza os dados de um usuário
	 */
	public void updateUser(User user)
	{
		int index = users.indexOf(user);
		users.set(index, user);
	}

	/**
	 * Remove um usuário, dado seu ID
	 */
	public void deleteUserById(long id)
	{
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();)
		{
			User user = iterator.next();
			
			if (user.getId() == id)
				iterator.remove();
		}
	}

	/**
	 * Verifica se existe um usuário com um email
	 */
	public boolean isUserExist(User user)
	{
		return findByEmail(user.getEmail()) != null;
	}

	/**
	 * Remove todos os usuários
	 */
	public void deleteAllUsers()
	{
		users.clear();
	}
}