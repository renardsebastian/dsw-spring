package br.unirio.dsw.selecaoppgi.view.edital;

import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa o formulário para edição de critérios de alinhamento
 * 
 * @author Renard Ferreira
 */
@SuppressWarnings("unused")
public @Data class AlinhamentoForm 
{
	private int idEdital = -1;
	private String codigoOriginal = "";
	private String codigo = "";
	private String nome = "";
	private int pesoComProvaOral;
	private int pesoSemProvaOral;
	private boolean pertenceProvaOral;
	private List<SubcriterioAlinhamento> subcriterios;
	private List<String> subcriteriosCodigo, subcriteriosNome, subcriteriosPeso, subcriteriosDescricao;
	
	/**
	 * Inicializa o formulário de provas escritas
	 */
	public AlinhamentoForm()
	{
		this.subcriterios = new ArrayList<SubcriterioAlinhamento>();
	}

	/**
	 * Adiciona um conjunto de subcritérios
	 */
	public void adicionaSubcriterios(Iterable<SubcriterioAlinhamento> subcriterios) 
	{		
		for (SubcriterioAlinhamento subcriterio : subcriterios)
			this.subcriterios.add(subcriterio);
	}
	
	public void removeSubcriterios(){
		this.subcriterios = new ArrayList<SubcriterioAlinhamento>();
	}

	/**
	 * Adiciona um subcritério
	 */
	public void adicionaSubcriterio(SubcriterioAlinhamento subcriterio) 
	{
		this.subcriterios.add(subcriterio);
	}
}