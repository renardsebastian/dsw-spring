package br.unirio.dsw.crud.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import br.unirio.dsw.crud.model.Role;
import br.unirio.dsw.crud.model.SignInProvider;
import br.unirio.dsw.crud.model.User;
import br.unirio.dsw.crud.utils.DateUtils;

/**
 * Classe responsavel pela persistencia de usuários
 * 
 * @author Marcio Barros
 */
public class UserDAO
{
	/**
	 * Carrega os dados de um usuário a partir do resultado de uma consulta
	 */
	private User load(ResultSet rs) throws SQLException
	{
		String name = rs.getString("nome");
		String email = rs.getString("email");
		String password = rs.getString("senha");

		User user = new User(name, email, password);
		user.setId(rs.getInt("id"));
		user.setRole(Role.get(rs.getInt("papel")));
		user.setSocialId(rs.getString("socialID"));
		user.setProvider(SignInProvider.get(rs.getInt("socialOrigem")));
		user.setMustResetPassword(rs.getInt("forcaResetSenha") != 0);
		user.setLastLoginDate(DateUtils.toDateTime(rs.getTimestamp("dataUltimoLogin")));
		user.setFailedLoginCounter(rs.getInt("contadorLoginFalha"));
		user.setLoginToken(rs.getString("tokenLogin"));
		user.setLoginTokenDate(DateUtils.toDateTime(rs.getTimestamp("dataTokenLogin")));
		return user;
	}

	/**
	 * Carrega um usuário, dado seu identificador
	 */
	public User getUserId(int id)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			User item = rs.next() ? load(rs) : null;
			c.close();
			return item;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.getUserId: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Carrega um usuário, dado seu e-mail
	 */
	public User getUserEmail(String email)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			User item = rs.next() ? load(rs) : null;
			
			c.close();
			return item;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.getUserEmail: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Conta o numero de usuários que atendem a um filtro
	 */
	public int count(String nameFilter, String emailFilter)
	{
		String SQL = "SELECT COUNT(*) " + 
					 "FROM Usuario " + 
					 "WHERE nome LIKE ? " + 
					 "AND email LIKE ? ";
		
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + nameFilter + "%");
			ps.setString(2, "%" + emailFilter + "%");

			ResultSet rs = ps.executeQuery();
			int count = rs.next() ? rs.getInt(1) : 0;

			c.close();
			return count;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.conta: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Retorna a lista de usuários registrados no sistema que atendem a um filtro
	 */
	public List<User> list(int pageNumber, int pageSize, UserOrderingField orderingField, UserOrderingCriteria orderingCriteria, String nameFilter, String emailFilter)
	{
		String SQL = "SELECT * " + 
					 "FROM User " + 
					 "WHERE nome LIKE ? " + 
					 "AND email LIKE ? ";
		
		String SQL_PAGE = "LIMIT ? OFFSET ?";

		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		List<User> lista = new ArrayList<User>();
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL + orderingField.getClause(orderingCriteria) + SQL_PAGE);
			ps.setString(1, "%" + nameFilter + "%");
			ps.setString(2, "%" + emailFilter + "%");
			ps.setInt(3, pageSize);
			ps.setInt(4, pageNumber * pageSize);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				User item = load(rs);
				lista.add(item);
			}

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.lista: " + e.getMessage());
		}
		    
		return lista;
	}

	/**
	 * Adiciona um usuário no sistema
	 */
	public boolean createUser(User user)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioInsere(?, ?, ?, ?, ?)}");
			cs.setString(1, user.getName());
			cs.setString(2, user.getUsername());
			cs.setString(3, user.getPassword());
			cs.setInt(4, user.getRole().getCodigo());
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			user.setId(cs.getInt(5));
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.create: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Atualiza a senha de um usuário
	 */
	public boolean updatePassword(int id, String password)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioTrocaSenha(?, ?)}");
			cs.setInt(1, id);
			cs.setString(2, password);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.updatePassword: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Registra o login de um usuário com sucesso
	 */
	public boolean registerSuccessfulLogin(int id)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioRegistraLoginSucesso(?)}");
			cs.setInt(1, id);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.registerSuccessfulLogin: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Registra o login de um usuário com falha
	 */
	public boolean registerFailedLogin(int id)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioRegistraLoginFalha(?)}");
			cs.setInt(1, id);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.registerFailedLogin: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Armazena um token de troca de senha para um usuário
	 */
	public boolean saveLoginToken(int id, String token)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioRegistraTokenResetSenha(?, ?)}");
			cs.setInt(1, id);
			cs.setString(2, token);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UserDAO.saveLoginToken: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Enumeracao dos campos de ordenacao de usuários
	 * 
	 * @author Marcio Barros
	 */
	public enum UserOrderingField
	{
		None(""),
		ID("id"),
		Name("nome"),
		Email("email");
		
		private String field;
		
		private UserOrderingField(String field)
		{
			this.field = field;
		}
		
		public String getClause(UserOrderingCriteria type)
		{
			if (field.length() == 0)
				return "";
			
			return "ORDER BY " + field + " " + type.getClause() + " ";
		}
	}
	
	/**
	 * Enumeracao dos tipos de ordenacao de usuários
	 * 
	 * @author Marcio Barros
	 */
	public enum UserOrderingCriteria
	{
		ASC("ASC"),
		DESC("DESC");
		
		private @Getter String clause;
		
		private UserOrderingCriteria(String campo)
		{
			this.clause = campo;
		}
	}
}