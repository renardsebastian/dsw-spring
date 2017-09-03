var provasEscritasController = function($scope, $log) {
	
	/**
	 * Recebe os dados do edital que está sendo editado
	 */
	var self = this;
	self.prova = {};
	self.indiceProvaSelecionada = -1;
	applyMaterialDesignLite();
	
	/**
	 * Apresenta os pesos de uma prova como uma string
	 */
	this.formataPesos = function(prova) {
		var len = prova.questoes.length;
		
		if (len == 0)
			return ""; 
		
		var result = prova.questoes[0];
		
		for (i = 1; i < len; i++)
			result += ", " + prova.questoes[i];
		
		return result;
	}

	/**
	 * Cria uma nova prova
	 */
	this.novaProva = function() {
		self.prova = { dispensavel: false, questoes: [100] };
		self.indiceProvaSelecionada = -1;
		abreJanelaEdicao();
	}

	/**
	 * Edita uma prova
	 */
	this.editaProva = function(index) {
		self.prova = angular.copy(self.edital.provas[index]);
		self.indiceProvaSelecionada = index;
		abreJanelaEdicao();
	}

	/**
	 * Abre a janela de edicao de provas
	 */
	function abreJanelaEdicao() {
	    var dialog = document.querySelector('dialog#dlgNovaProvaEscrita');
	    
	    if (dialog) {
		    dialog.querySelector('.close').addEventListener('click', function() {
		        dialog.close();
		    });	
		    
			dialog.showModal();
	    }
	}
	
	/**
	 * Remove uma prova
	 */
	this.removeProva = function(index) {
		this.edital.provas.splice(index, 1);
		$scope.$apply();
	}
	
	/**
	 * Registra uma nova questao na prova sendo editada
	 */
	this.novaQuestao = function() {
		var prova = this.prova || {};
		var questoes = prova.questoes || [];
		questoes.push(0);
	//	this.prova = prova;
	}
	
	/**
	 * Remove uma questao da prova sendo editada
	 */
	this.removeQuestao = function(index) {
		if (index > 0)
			this.prova.questoes.splice(index, 1);
	}

	/**
	 * Salva a prova
	 */
	this.salvaProva = function() {
		$log.info(self.prova);
		
		if (!self.prova.codigo || self.prova.codigo.length != 4)
			return showError("O código deve ter 4 dígitos.");
		
		if (!self.prova.nome || self.prova.nome.length == 0)
			return showError("O nome da prova não pode ficar vazio.");
		
		if (!self.prova.notaMinima || self.prova.notaMinima <= 0)
			return showError("A nota mínima para aprovação na prova deve ser maior do que zero.");
		
		if (self.prova.notaMinima >= 100)
			return showError("A nota mínima para aprovação na prova deve ser menor do que 100.");
		
		if (!self.edital.provas)
			self.edital.provas = [];

		if (self.indiceProvaSelecionada == -1)
			self.edital.provas.push(self.prova);
		else
			self.edital.provas[self.indiceProvaSelecionada] = self.prova;
		
	    document.querySelector('dialog#dlgNovaProvaEscrita').close();
	}
};

App.component("provasEscritas", {
	templateUrl: contextPath + "/static/js/edital/prova.component.template.html?2",
	controller: provasEscritasController,
	bindings: { edital: '=' }
});
