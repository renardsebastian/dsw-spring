var App = angular.module('ppgiSelecaoApp', ['pascalprecht.translate']);

App.controller("formController", function ($scope, dataService, $log) {

	/**
	 * Filtros
	 */
	$scope.edital = {
		id: -1
	}

	/**
	 * Programa principal
	 */
	$scope.edital.id = id;
	
	if (id != -1) {
		dataService.carrega(id).then(function(data) {
			$log.info(data);
			$scope.edital = data.data;
		});
	}

});