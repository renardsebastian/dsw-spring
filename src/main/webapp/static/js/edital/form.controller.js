App.controller("formController", function ($scope, formEditalDataService, $log, $window) {

	/**
	 * Dados do edital sendo editado
	 */
	$scope.edital = {
		id: -1
	}
	
	$scope.prova = {};
	
	
	$scope.submeteEdital = function() {
		$log.info($scope.edital);
		formEditalDataService.atualiza($scope.edital).then(function(data) {
			if (!checkForErrors(data.data)) {
				window.location = contextPath + "/edital/list?message=edital.form.dados.salvos.sucesso";
			}
		});
	}

	$scope.retornaLista = function() {
		window.location = contextPath + "/edital/list";
	}

	$scope.formataPesos = function(prova) {
		var len = prova.questoes.length;
		
		if (len == 0)
			return ""; 
		
		var result = prova.questoes[0];
		
		for (i = 1; i < len; i++)
			result += ", " + prova.questoes[i];
		
		return result;
	}
	
	$scope.novaProvaEscrita = function() {
		$scope.prova = { questoes: [100] };
		
	    var dialog = document.querySelector('dialog#dlgNovaProvaEscrita');
	    
	    if (dialog) {
		    dialog.querySelector('.close').addEventListener('click', function() {
		        dialog.close();
		    });	
		    
			dialog.showModal();
	    }
	}
	
	$scope.removeProva = function(index) {
		$log.info($scope.edital);
		$scope.edital.provas.splice(index, 1);
		$scope.$apply();
	}
	
	$scope.novaQuestaoProvaEscrita = function() {
		var prova = $scope.prova || {};
		var questoes = prova.questoes || [];
		questoes.push(0);
		$scope.prova = prova;
	}
	
	$scope.removeQuestao = function(index) {
		if (index > 0)
			$scope.prova.questoes.splice(index, 1);
	}
	
	$scope.salvaProva = function() {
		$log.info($scope.prova);
		
		if (!$scope.prova.codigo || $scope.prova.codigo.length != 4)
			return showError("O código deve ter 4 dígitos.");
		
		if (!$scope.prova.nome || $scope.prova.nome.length > 0)
			return showError("O nome da prova não pode ficar vazio.");
		
		if (!$scope.prova.notaMinima || $scope.prova.notaMinima > 0)
			return showError("O nota mínima para aprovação na prova deve ser maior do que zero.");
		
		if (!$scope.prova.notaMinima || $scope.prova.notaMinima < 100)
			return showError("O nota mínima para aprovação na prova deve ser menor do que 100.");
		
		$scope.edital.provas.push($scope.prova);
	}
	
	/**
	 * Programa principal
	 */
	$scope.edital.id = id;
	$log.info(id);

	if (id != -1) {
		formEditalDataService.carrega(id).then(function(data) {
			$scope.edital = data.data.data;
			$log.info($scope.edital);
		});
	}

});