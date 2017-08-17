var App = angular.module('ppgiSelecaoApp', ['pascalprecht.translate', 'unirio.dsw', 'ngTable']);

App.controller("listaController", function ($scope, dataService, NgTableParams) {

	/**
	 * Filtros
	 */
	$scope.filtros = {
		nome: ""
	}
	
	/*
	 * Altera os filtros de consulta
	 */
	$scope.atualizaFiltro = function () {
		atualizaLista();
	}
	
	/*
	 * Atualiza a lista de editais
	 */
	var atualizaLista = function() {
		$scope.tableParams.reload();
	}
	
	/*
	 * Navega para a pagina de visualizacao de edital
	 */
	$scope.edita = function(id) {
		window.location = contextPath + "/edital/edit/" + id;
	}
	
	/*
	 * Cria um novo edital
	 */
	$scope.novo = function() {
		window.location = contextPath + "/edital/create";
	}
	
	/*
	 * Remove o edital selecionado
	 */
	$scope.remove = function(id) {
		dataService.remove(id).then(atualizaLista);
	}
	
	/*
	 * Prepara a tabela
	 */
	$scope.tableParams = new NgTableParams({}, {
		getData: function (params) {
			return dataService.lista({
				page: params.page() - 1,
				size: params.count(),
				nome: $scope.filtros.nome
			}).then(function (data) {
				if(data.data.TotalRecordCount == 0) {
					$scope.noSite = true;
				}
				else {
					params.total(data.data.TotalRecordCount);
					$scope.noSite = false;
					return data = data.data.Records;
				}
			});
		}
	});
	
});