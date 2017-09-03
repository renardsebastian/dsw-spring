<%@include file="/WEB-INF/views/helper/template.jsp" %>

<script>
var id = "${id}";
</script>

<div id="contents" data-ng-controller="formController as ctrl">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--12-col page-header">
			<h3><spring:message code="edital.form.title"/></h3>
		</div>
	</div>
	
   	<div class="mdl-grid">
       	<!-- Campos básicos -->
        <div class="mdl-cell mdl-cell--6-col" style="padding-right: 80px;">
			<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
			    <input ng-model="ctrl.edital.nome" class="mdl-textfield__input" type="text" name="nome" id="nome">
			    <label class="mdl-textfield__label" for="nome">
			    		<spring:message code="edital.form.label.nome"/>:
			    </label>
			</div>

			<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
			    <input ng-model="ctrl.edital.notaMinimaAlinhamento" class="mdl-textfield__input" type="text" pattern="[0-9]+" name="notaMinima" id="nota-minima-alinhamento">
			    <label class="mdl-textfield__label" for="nota-minima-alinhamento">
			    		<spring:message code="edital.form.label.nota.minima.alinhamento"/>:
			    </label>
			</div>
		</div>
			
		<!-- Janela de edicao de nova prova -->
        <div class="mdl-cell mdl-cell--6-col">
			<provas-escritas edital="ctrl.edital"></provas-escritas>
		</div>
	</div>
	

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

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/third-party/ngMaterialLight/ngMaterialLight.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.controller.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.dataService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/prova.component.controller.js"></script>
