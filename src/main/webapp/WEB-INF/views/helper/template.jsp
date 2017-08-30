<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="pt" ng-app="ppgiSelecaoApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Login">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>PPGI/UNIRIO</title>

    <!-- Page styles -->
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" type="text/css" href="https://code.getmdl.io/1.2.1/material.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/third-party/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/third-party/twitter/css/bootstrap-social.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/styles.css">
</head>

<script>
var csrf = { name: "${_csrf.parameterName}", value: "${_csrf.token}" };
var contextPath = "${pageContext.request.contextPath}";
</script>

<body>
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

        <!-- Header -->
        <header class="mdl-layout__header" data-ng-controller="TopNavigatorController">
            <div class="mdl-layout__header-row">
                <!-- Title -->
                <span class="mdl-layout-title">
                    PPGI/UNIRIO: Sistema de seleção
                </span>

                <!-- Spacer -->
                <div class="mdl-layout-spacer">
                </div>

                <!-- Selected item -->
                <nav class="mdl-navigation">
					<sec:authorize access="isAuthenticated()">
	                		<div class="editalSelecionado">
	                			<div class="titulo">
	                				<spring:message code="template.titulo.edital.selecionado"/>:
	                			</div>
	                			<div>
			                		<c:if test="${empty session.edital}">
					                	<spring:message code="template.titulo.edital.nenhum"/>
					            </c:if>
			                		<c:if test="${not empty session.edital}">
					                	${session.edital.nome}
					            </c:if>
					        </div>
	                		</div>
	                	</sec:authorize>
                </nav>

                <!-- Spacer -->
                <div class="mdl-layout-spacer">
                </div>

                <!-- Navigation -->
                <nav class="mdl-navigation">
					<sec:authorize access="isAuthenticated()">
	                    <div class="usuarioLogado">
	                    		<div class="cumprimento">
		                    		<spring:message code="template.label.ola"/>, <sec:authentication property="principal.nome"/>!
	                    		</div>
	                    		<div class="dataLogin">
		                    		<sec:authentication var="user" property="principal" />
		                    		<c:if test="${not empty user.dataUltimoLogin}">
		                    			Último login em ${user.dataUltimoLoginFormatada} 
		                    		</c:if>
	                    		</div>
	                    </div>
	                    <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/login/change">
	                    		<spring:message code="template.comando.trocasenha"/>
	                    </a>
	                    <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/logout">
	                    		<spring:message code="template.comando.logout"/>
	                    </a>
                    </sec:authorize>      
                </nav>
            </div>
        </header>


        <!-- Side bar -->
        <div id="sideBar" class="mdl-layout__drawer">
            <span class="mdl-layout-title">
                Opções
            </span>
        </div>


        <!-- Central stage -->
        <main id="centralStage" class="mdl-layout__content">
        </main>


        <!-- Footer -->
        <footer class="android-footer mdl-mega-footer">
            <div class="mdl-mega-footer--top-section">
                <div class="mdl-mega-footer--left-section">
                  <p class="mdl-typography--font-light">PPGI/UNIRIO: ©2017</p>
                </div>
            </div>
        </footer>
    </div>
  
  
  	<!-- Snackbar -->
	<div id="demo-snackbar-example" class="mdl-js-snackbar mdl-snackbar">
	  <div class="mdl-snackbar__text"><spring:message code='${param.message}'/></div>
	  <button class="mdl-snackbar__action" type="button"></button>
	</div>

	
	<!-- Janela de selecao de edital -->
	<dialog class="mdl-dialog">
	    <h4 class="mdl-dialog__title">
	    		<spring:message code="template.dialogo.seleciona.edital.titulo"/>
	    </h4>
	    <div class="mdl-dialog__content">
	      	<p>
	        		<spring:message code="template.dialogo.seleciona.edital.subtitulo"/>
	      	</p>
	      	<p>
	      		<select ng-model="editalSelecionado">
	      			<option ng-repeat="edital in editais" value="{{edital.id}}">{{edital.nome}}</option>
				</select>
	      	</p>
	    	</div>
	    <div class="mdl-dialog__actions">
	      	<button type="button" class="mdl-button">
	      		<spring:message code="template.dialogo.seleciona.edital.botao.ok"/>
			</button>
	      	<button type="button" class="mdl-button close">
	      		<spring:message code="template.dialogo.seleciona.edital.botao.cancela"/>
	      	</button>
	    </div>
	</dialog>


    <!-- Material design -->
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/third-party/ngTranslate/angular-translate.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/app.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/helper/navigator.dataService.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/helper/navigator.controller.js"></script>

	<script>
	angular.element(document).ready(function () {
		/* Copia o conteudo para o painel central */
	    var centralStageElement = document.getElementById('centralStage');
	    var contentsElement = document.getElementById('contents');
	    centralStageElement.appendChild(contentsElement);

		/* Monta o menu lateral */
	    var sideBarElement = document.getElementById('sideBar');
	    var sideMenuElement = document.getElementById('sideMenu');
	    
	    if (sideMenuElement)
		    sideBarElement.appendChild(sideMenuElement);
		else
		   sideBarElement.remove();
	});
	</script>

	<c:if test="${not empty param.message}">
		<script>
		window.onload = function () {
		    var snackbar = document.querySelector('#demo-snackbar-example');
		    var message = snackbar.getElementsByClassName("mdl-snackbar__text")[0].textContent;
		    snackbar.MaterialSnackbar.showSnackbar({ 
		    	message: message, 
		    	timeout: 20000,
		        actionHandler: function() { snackbar.MaterialSnackbar.cleanup_() },
		        actionText: 'x' 
		    });
        };
		</script>
	</c:if>
</body>
</html>