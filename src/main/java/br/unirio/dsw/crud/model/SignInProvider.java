package br.unirio.dsw.crud.model;

import lombok.Getter;

/**
 * Provedor de serviço de sign-in
 * 
 * @author marciobarros
 */
public enum SignInProvider 
{
    FACEBOOK(0),
    TWITTER(1);
    
    private @Getter int codigo;
    
    private SignInProvider(int codigo)
    {
    	this.codigo = codigo;
    }
    
    public static SignInProvider get(int codigo)
    {
    	for (SignInProvider provider : values())
    		if (provider.getCodigo() == codigo)
    			return provider;
    	
    	return null;
    }
}