<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="/WEB-INF/views/helper/template.jsp" %>

<script>
var id = "${id}";
</script>

<div id="contents" data-ng-controller="formController as ctrl">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--12-col page-header">
        	<c:if test="${not empty edital}">
				<h3><spring:message code="edital.form.titulo.edicao"/></h3>
			</c:if>
        	<c:if test="${empty edital}">
				<h3><spring:message code="edital.form.titulo.criacao"/></h3>
			</c:if>
		</div>
	</div>
	
	<form:form action="${pageContext.request.contextPath}/edital/save" commandName="form" method="POST" enctype="utf8" role="form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:hidden path="id"/>
	   	
       	<!-- Campos básicos -->
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--12-col">
				<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				    <form:input path="nome" id="edital-nome" class="mdl-textfield__input" />
	                   <form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
				    <label class="mdl-textfield__label" for="edital-nome"><spring:message code="edital.form.label.nome"/>:</label>
				</div>
			</div>
		</div>
	
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--12-col">
				<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				    <form:input path="notaMinimaAlinhamento" id="edital-notaMinima" class="mdl-textfield__input" type="number" pattern="[0-9]+" />
                    <form:errors id="error-notaMinima" path="notaMinimaAlinhamento" cssClass="error-block"/>
				    <label class="mdl-textfield__label" for="edital-notaMinima"><spring:message code="edital.form.label.nota.minima.alinhamento"/>:</label>
				</div>
			</div>
		</div>
				
		<!-- Janela de edicao de nova prova -->
       	<c:if test="${not empty edital}">
	        <div class="mdl-cell mdl-cell--12-col">
				<provas-escritas edital="ctrl.edital"></provas-escritas>
			</div>
		</c:if>

		<!-- Botoes de comando -->
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--12-col text-right">
				<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect" ng-click="ctrl.submeteEdital()">
					<spring:message code="edital.form.botao.ok"/>
				</button>
				<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect" ng-click="ctrl.retornaLista()">
					<spring:message code="edital.form.botao.cancela"/>
				</button>
		    </div>
		</div>
	</form:form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/third-party/ngMaterialLight/ngMaterialLight.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.controller.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.dataService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/prova.component.controller.js"></script>
