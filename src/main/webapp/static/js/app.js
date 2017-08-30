'use strict';
 
var App = angular.module('ppgiSelecaoApp', ['pascalprecht.translate', 'unirio.dsw', 'ngTable']);

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function applyMaterialDesignLite() {
	if(!(typeof(componentHandler) == 'undefined')){
	    componentHandler.upgradeAllRegistered();
	}
}

function checkForErrors(data) {
	if (data.result == "FAIL") {
		showError(data.message);
		return true;
	}
	
	return false;
}

function showError(message) {
    var snackbar = document.querySelector('#demo-snackbar-example');
    
    snackbar.MaterialSnackbar.showSnackbar({ 
	    	message: message, 
	    	timeout: 20000,
        actionHandler: function() { snackbar.MaterialSnackbar.cleanup_() },
        actionText: 'x' 
    });
    
    return false;
}