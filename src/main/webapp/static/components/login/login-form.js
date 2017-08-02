/*
 * Traducao
 */
App.config(function($translateProvider) {
	$translateProvider.translations('pt', {
		form_title: 'Faça login usando a sua conta',
		login_failed_error: 'Credenciais inválidas',
		label_email: 'E-mail',
		label_password: 'Senha',
		button_submit: 'Envia',
		button_create_account: 'Criar nova conta',
		button_forgot_password: 'Esqueci minha senha',
		signin_title: '... ou usando uma rede social',
		siging_facebook: 'Login com Facebook',
		siging_twitter: 'Login com Twitter'
	})
	
	.translations('en', {
		form_title: 'Login using your account',
		login_failed_error: 'Invalid credentials',
		label_email: 'E-mail',
		label_password: 'Password',
		button_submit: 'Send',
		button_create_account: 'Create new account',
		button_forgot_password: 'Forgot my password',
		signin_title: '... or using a social network.',
		siging_facebook: 'Login with Facebook',
		siging_twitter: 'Login with Twitter'
	});
	
	$translateProvider.preferredLanguage('en');
});


/*
 * Controller
 */
var loginFormController = function ($scope, $window, $translate) {
	$scope.error = getUrlParameter("error") ? $translate.instant("login_failed_error") : "";
	$scope.csrf = csrf;
	applyMaterialDesignLite();

	$scope.forgot = function() {
		$window.location.href = contextPath + "/login/forgot";
	}
	  
	$scope.create = function() {
		$window.location.href = contextPath + "/login/register";
	}
	  
	$scope.loginTwitter = function() {
		$window.location.href = contextPath + "/auth/twitter";
	}
	  
	$scope.loginFacebook = function() {
		$window.location.href = contextPath + "/auth/facebook";
	}
}


/*
 * Componente
 */
App.component('loginForm', {
	templateUrl: '/crud/static/components/login/login-form.html',
	controller:  loginFormController
});