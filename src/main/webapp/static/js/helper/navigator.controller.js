var App = angular.module('ppgiSelecaoApp', ['pascalprecht.translate']);

App.controller("TopNavigatorController", function ($scope, dataService, $log) {

	$scope.editais = [];
	$scope.csrf = csrf;

	/**
	 * Programa principal
	 */
	dataService.carrega().then(function(data) {
		$log.info(data);
		$scope.editais = data;
	});

    var dialog = document.querySelector('dialog');
//    var showDialogButton = document.querySelector('#show-dialog');
    
    if (!dialog.showModal) {
    		dialogPolyfill.registerDialog(dialog);
    }
    
//    showDialogButton.addEventListener('click', function() {
//      dialog.showModal();
//    });
    
//    dialog.querySelector('.close').addEventListener('click', function() {
//      dialog.close();
//    });	 

});