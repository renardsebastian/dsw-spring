<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="/WEB-INF/views/helper/template.jsp" %>

<script>
var id = "${id}";
</script>

<div id="contents">

    <!-- Campos básicos -->
	<form:form action="${pageContext.request.contextPath}/edital/edit" commandName="form" method="POST" enctype="utf8" role="form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:hidden path="id"/>
	   	
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--1-col">
	        </div>
	        
	        <div class="mdl-cell mdl-cell--10-col">
			    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
			        <!-- Form header -->
			        <div class="mdl-card__title">
			            <h2 class="mdl-card__title-text">
		            			<spring:message code="edital.form.titulo.edicao"/>
			            </h2>
			        </div>
			
			        <!-- Form body -->
				    <div class="mdl-card__supporting-text">
						<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="nome" id="edital-nome" class="mdl-textfield__input" type="text" value="${form.nome}"/>
			                <form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="edital-nome"><spring:message code="edital.form.label.nome"/>:</label>
						</div>
	
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="notaMinimaAlinhamento" id="edital-notaMinima" class="mdl-textfield__input" type="number" pattern="[0-9]+"/>
		                    <form:errors id="error-notaMinima" path="notaMinimaAlinhamento" cssClass="error-block"/>
						    <label class="mdl-textfield__label" for="edital-notaMinima"><spring:message code="edital.form.label.nota.minima.alinhamento"/>:</label>
						</div>
					</div>
					
			        <!-- Form buttons -->
			        <div class="mdl-card__actions mdl-card--border">
			       	 	<div class="left">
			            </div>
			            <div class="right">
							<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
								<spring:message code="edital.form.botao.salva"/>
							</button>
			            		<a href="${pageContext.request.contextPath}/edital/list">
								<button type="button" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
									<spring:message code="edital.form.botao.retorna"/>
								</button>
							</a>
					    </div>
			        </div>
				</div>
			</div>

	        <div class="mdl-cell mdl-cell--1-col">
	        </div>	        
		</div>
	</form:form>

				
	<!-- Provas escritas -->
   	<div class="mdl-grid">
        <div class="mdl-cell mdl-cell--1-col">
        </div>
        
        <div class="mdl-cell mdl-cell--10-col">
			<div>
				<div class="left">
					<h4 class="list-title">
						<spring:message code="edital.form.titulo.provas.escritas"/>
					</h4>
				</div>
				<div class="right">
					<a href="${pageContext.request.contextPath}/edital/${id}/prova/create">
						<button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
							<spring:message code="edital.form.botao.nova.prova"/>
						</button>
					</a>
				</div>
				<div class="clear">
				</div>
			</div>
			
			<table class="wide mdl-data-table mdl-js-data-table mdl-shadow--2dp">
			<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.coluna.codigo"/></th>
				<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.coluna.nome"/></th>
				<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.coluna.dispensavel"/></th>
				<th><spring:message code="edital.form.coluna.nota.minima"/></th>
				<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.coluna.pesos"/></th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="prova" items="${edital.provasEscritas}">
			<tr>
				<td class="mdl-data-table__cell--non-numeric">${prova.codigo}</td>
				<td class="mdl-data-table__cell--non-numeric">${prova.nome}</td>
				<td class="mdl-data-table__cell--non-numeric">${prova.dispensavel ? "S" : "N"}</td>
				<td>${prova.notaMinimaAprovacao}</td>
				<td class="mdl-data-table__cell--non-numeric">${prova.pesosQuestoes}</td>
				<td>
					<a href="${pageContext.request.contextPath}/edital/${id}/prova/edit/${prova.codigo}">
						<button class="mdl-button mdl-js-button mdl-button--icon" ng-click="$ctrl.editaProva($index)">
							<i class="material-icons">edit</i>
						</button></a>
					<a href="${pageContext.request.contextPath}/edital/${id}/prova/remove/${prova.codigo}">
						<button class="mdl-button mdl-js-button mdl-button--icon"> <!-- confirmed-click="$ctrl.removeProva('${prova.codigo}'})" ng-confirm-click="{{'edital.form.mensagem.confirma.remocao.prova' | translate}}"> -->
							<i class="material-icons">delete</i>
						</button></a>
				</td>
			</tr>
			</c:forEach>
			<c:if test="${empty edital.provasEscritas}">
			<tr>
				<td class="mdl-data-table__cell--non-numeric" colspan="7">
					<spring:message code="edital.form.nenhuma.prova.disponivel"/>
				</td>
			</tr>
			</c:if>
			</tbody>
			</table>
		</div>

        <div class="mdl-cell mdl-cell--1-col">
        </div>
	</div>

</div>