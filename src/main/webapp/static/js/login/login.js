App.controller('LoginController', ['$scope', '$window', function($scope, $window) {

	$scope.forgot = function() {
		$window.location.href = contextPath + "/login/forgot";
	}
	  
	$scope.create = function() {
		$window.location.href = contextPath + "/login/create";
	}
	  
	$scope.loginTwitter = function() {
		$window.location.href = contextPath + "/auth/twitter";
	}
	  
	$scope.loginFacebook = function() {
		$window.location.href = contextPath + "/auth/facebook";
	}
}]);