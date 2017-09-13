App.controller("formProvaController", function ($scope, $log, $window) {

	/**
	 * Recebe os dados do edital que está sendo editado
	 */
	var self = this;
	self.prova = {};
	self.indiceProvaSelecionada = -1;
	applyMaterialDesignLite();
	loadTranslations("edital.form", $rootScope, $http);

	/**
	 * Apresenta os pesos de uma prova como uma string
	 */
	self.formataPesos = function(prova) {
		var len = prova.pesosQuestoes.length;
		
		if (len == 0)
			return ""; 
		
		var result = prova.pesosQuestoes[0];
		
		for (i = 1; i < len; i++)
			result += ", " + prova.pesosQuestoes[i];
		
		return result;
	}

	/**
	 * Cria uma nova prova
	 */
	self.novaProva = function() {
		self.prova = { dispensavel: false, pesosQuestoes: [100] };
		self.indiceProvaSelecionada = -1;
		abreJanelaEdicao();
	}

	/**
	 * Edita uma prova
	 */
	self.editaProva = function(index) {
		self.prova = angular.copy(self.edital.provasEscritas[index]);
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
	self.removeProva = function(index) {
		self.edital.provasEscritas.splice(index, 1);
		$scope.$apply();
	}
	
	/**
	 * Registra uma nova questao na prova sendo editada
	 */
	self.novaQuestao = function() {
		var prova = self.prova || {};
		var pesosQuestoes = prova.pesosQuestoes || [];
		pesosQuestoes.push(0);
	}
	
	/**
	 * Remove uma questao da prova sendo editada
	 */
	self.removeQuestao = function(index) {
		if (index > 0)
			self.prova.pesosQuestoes.splice(index, 1);
	}

	/**
	 * Salva a prova
	 */
	self.salvaProva = function() {	

		if (!self.prova.codigo || self.prova.codigo.length != 4)
			return showError(translate($rootScope, "edital.form.prova.form.erro.codigo.invalido"));
		
		if (!self.prova.nome || self.prova.nome.length == 0)
			return showError(translate($rootScope, "edital.form.prova.form.erro.nome.branco"));
		
		if (!self.prova.notaMinimaAprovacao || self.prova.notaMinimaAprovacao <= 0)
			return showError(translate($rootScope, "edital.form.prova.form.erro.nota.minima.menor.zero"));
		
		if (self.prova.notaMinimaAprovacao >= 100)
			return showError(translate($rootScope, "edital.form.prova.form.erro.nota.minima.maior.cem"));
		
		var soma = 0;
		
		for (var i = 0; i < self.prova.pesosQuestoes.length; i++) {
			if (self.prova.pesosQuestoes[i] < 0)
				return showError(translate($rootScope, "edital.form.prova.form.erro.peso.negativo.zero"));

			if (self.prova.pesosQuestoes[i] > 100)
				return showError(translate($rootScope, "edital.form.prova.form.erro.peso.maior.cem"));

			soma += self.prova.pesosQuestoes[i];
		}

		if (soma != 100)
			return showError(translate($rootScope, "edital.form.prova.form.erro.peso.soma.diferente.cem"));
		
		if (!self.edital.provas)
			self.edital.provas = [];

		if (self.indiceProvaSelecionada == -1)
			self.edital.provasEscritas.push(self.prova);
		else
			self.edital.provasEscritas[self.indiceProvaSelecionada] = self.prova;
		
	    document.querySelector('dialog#dlgNovaProvaEscrita').close();
	}
});