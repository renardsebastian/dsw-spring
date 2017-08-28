package br.unirio.dsw.selecaoppgi.model.usuario;

import lombok.Getter;

/**
 * Enumeração dos papéis que podem ser assumidos pelos usuários
 * 
 * @author marciobarros
 */
public enum PapelUsuario 
{
    ROLE_BASIC(0),
    ROLE_OFFICE(1),
    ROLE_PROFESSOR(2),
    ROLE_ROOT(3);
    
    private @Getter int codigo;
    
    private PapelUsuario(int codigo)
    {
    	this.codigo = codigo;
    }
    
    public static PapelUsuario get(int codigo)
    {
    	for (PapelUsuario role : values())
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