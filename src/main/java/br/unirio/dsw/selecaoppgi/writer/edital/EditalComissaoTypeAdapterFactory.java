package br.unirio.dsw.selecaoppgi.writer.edital;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import br.unirio.dsw.selecaoppgi.model.usuario.Usuario;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;

/**
 * Classe que representa um serializador de geração de representação JSON
 * 
 * @author Marcio Barros
 */
public class EditalComissaoTypeAdapterFactory implements TypeAdapterFactory
{
	@Override
    @SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
	{
		if (type.getRawType() != List.class)
			return null;
		
		if (!(type.getType() instanceof ParameterizedType))
			return null;
		
		ParameterizedType supertype = (ParameterizedType) type.getType();
		
		if (supertype.getActualTypeArguments().length != 1 || supertype.getActualTypeArguments()[0] != Usuario.class)
			return null;
		
		return (TypeAdapter<T>) new ComissaoEditalAdapter();
	}

	/**
	 * Classe que publica e carrega as comissõe de um edital a partir da sua representação JSON
	 * 
	 * @author Marcio Barros
	 *
	 */
	private static class ComissaoEditalAdapter extends TypeAdapter<List<Usuario>>
	{
		@Override
		public void write(JsonWriter out, List<Usuario> value) throws IOException
		{
			out.beginArray();
		
			for (Usuario usuario : value)
			{
				out.value(usuario.getId());
			}
			
			out.endArray();
		}

		@Override
		public List<Usuario> read(JsonReader in) throws IOException
		{
			List<Usuario> usuarios = new ArrayList<Usuario>();
			in.beginArray();
			
			while (in.hasNext())
			{
				int id = in.nextInt();
				Usuario usuario = new UsuarioDAO().carregaUsuarioId(id);
				usuarios.add(usuario);
			}
			
			in.endArray();
			return usuarios;
		}
	}
}