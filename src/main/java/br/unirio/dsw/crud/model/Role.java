package br.unirio.dsw.crud.model;

import lombok.Getter;

/**
 * Papéis que podem ser assumidos pelos usuários
 * 
 * @author marciobarros
 */
public enum Role 
{
    ROLE_BASIC(0),
    ROLE_OFFICE(1),
    ROLE_PROFESSOR(2),
    ROLE_ROOT(3);
    
    private @Getter int codigo;
    
    private Role(int codigo)
    {
    	this.codigo = codigo;
    }
    
    public static Role get(int codigo)
    {
    	for (Role role : values())
    		if (role.getCodigo() == codigo)
    			return role;
    	
    	return null;
    }
    
    @Override
    public String toString()
    {
    	return name();
    }
}