App.controller("TopNavigatorController", function ($scope, topNavigatorDataService, $log, $window) {

	$scope.editalSelecionado = -1;
	$scope.editais = [];
	$scope.csrf = csrf;
	
	$scope.abreJanelaSelecaoEdital = function() {
		topNavigatorDataService.carrega().then(function(data) {
			$scope.editais = data.data;
		});

		dialog.showModal();
	}
	
	$scope.selecionaEdital = function() {
		topNavigatorDataService.mudaEditalSelecionado($scope.editalSelecionado, csrf);
		$window.location.href = contextPath + "/?message=edital.muda.selecionado.sucesso";
	}

	/**
	 * Programa principal
	 */
    var dialog = document.querySelector('dialog');
    
    if (dialog) {
	    dialog.querySelector('.close').addEventListener('click', function() {
	        dialog.close();
	    });	 
    }
});