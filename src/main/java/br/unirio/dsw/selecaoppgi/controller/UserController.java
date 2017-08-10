package br.unirio.dsw.selecaoppgi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.unirio.dsw.selecaoppgi.model.User;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO.UserOrderingCriteria;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO.UserOrderingField;

/**
 * Controller responsável pelo gerenciamento de usuários
 * 
 * @author marciobarros
 */
@RestController
public class UserController
{
	@Autowired
	private UserDAO userDAO; 

	/**
	 * Ação que lista todos os usuários
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers()
	{
		List<User> users = userDAO.list(0, 10000, UserOrderingField.Name, UserOrderingCriteria.ASC, "", "");
		
		if (users.isEmpty())
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/**
	 * Ação que recupera um usuário
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") int id)
	{
		User user = userDAO.getUserId(id);
		
		if (user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Ação que cria um novo usuário
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder)
	{
		if (userDAO.getUserEmail(user.getUsername()) != null)
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);

		userDAO.createUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Ação que atualiza os dados de um usuário
	 */
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
//	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user)
//	{
//		User currentUser = userService.findById(id);
//
//		if (currentUser == null)
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//
//		currentUser.setEmail(user.getEmail());
//		userService.updateUser(currentUser);
//		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//	}

	/**
	 * Ação que remove um usuário
	 */
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteUser(@PathVariable("id") long id)
//	{
//		User user = userService.findById(id);
//		
//		if (user == null)
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//
//		userService.deleteUserById(id);
//		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//	}

	/**
	 * Ação que remove todos os usuários
	 */
//	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteAllUsers()
//	{
//		userService.deleteAllUsers();
//		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//	}
}