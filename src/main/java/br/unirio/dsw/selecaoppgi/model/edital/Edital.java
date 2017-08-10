package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.List;

import org.joda.time.DateTime;

import br.unirio.dsw.selecaoppgi.model.User;
import lombok.Getter;
import lombok.Setter;

public class Edital
{
	private @Getter @Setter int id;
	private @Getter @Setter String nome;
	private @Getter @Setter DateTime dataInicio;
	private @Getter @Setter DateTime dataTermino;
	private @Getter @Setter StatusEdital status;
	private @Getter @Setter int notaMinimaAlinhamento;
	private List<User> comissaoSelecao;
	private List<User> comissaoRecursos;
	private List<ProvaEscrita> provasEscritas;
	private List<ProjetoPesquisa> projetosPesquisa;
	private List<CriterioAlinhamento> criteriosAlinhamento;
}