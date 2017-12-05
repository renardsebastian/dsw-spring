<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">

    <!-- Campos básicos -->
	<form:form action="${pageContext.request.contextPath}/edital/alinhamento/save" commandName="form" method="POST" enctype="utf8" role="form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:hidden path="idEdital"/>
		<form:hidden path="codigoOriginal"/>
	   	
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--1-col">
	        </div>
	        
	        <div class="mdl-cell mdl-cell--10-col">
			    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
			        <!-- Form header -->
			        <div class="mdl-card__title">
			            <h2 class="mdl-card__title-text">
		            			<spring:message code="edital.form.titulo.criterios.alinhamento.edicao"/>
			            </h2>
			        </div>
			        <!-- Form body -->
				    <div class="mdl-card__supporting-text">
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="codigo" id="alinhamento-codigo" class="mdl-textfield__input" type="text" value="${form.codigo}"/>
			                <form:errors id="error-codigo" path="codigo" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="alinhamento-codigo"><spring:message code="edital.form.label.codigo"/>:</label>
						</div>
	
						<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="nome" id="alinhamento-nome" class="mdl-textfield__input" type="text" value="${form.nome}"/>
			                <form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="alinhamento-nome"><spring:message code="edital.form.label.nome"/>:</label>
						</div>
						
						<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect alinhamento-pertence-prova-oral" for="alinhamento-pertenceProvaOral">							
							<span class="mdl-checkbox__label"><spring:message code="edital.form.label.pertenceProvaOral"/></span>
							<form:checkbox path="pertenceProvaOral" id="alinhamento-pertenceProvaOral" class="mdl-checkbox__input"/>
						</label>
						
						
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="pesoComProvaOral" id="alinhamento-pesoComOral" class="mdl-textfield__input" type="number" pattern="\d{1,2}|100" value="${form.pesoComProvaOral}"/>
		                    <form:errors id="error-pesoComOral" path="pesoComProvaOral" cssClass="error-block"/>
						    <label class="mdl-textfield__label" for="alinhamento-pesoComOral"><spring:message code="edital.form.coluna.peso.com.prova.oral"/>:</label>
						</div>
						
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="pesoSemProvaOral" id="alinhamento-pesoSemOral" class="mdl-textfield__input" type="number" pattern="\d{1,2}|100" value="${form.pesoSemProvaOral}"/>
		                    <form:errors id="error-pesoSemOral" path="pesoSemProvaOral" cssClass="error-block"/>
						    <label class="mdl-textfield__label" for="alinhamento-pesoSemOral"><spring:message code="edital.form.coluna.peso.sem.prova.oral"/>:</label>
						</div>
						
						<div style="margin-top: 20px;">
							<div class="left">
								<h4 class="list-title">
									<spring:message code="edital.form.titulo.subcriterios"/>
								</h4>
							</div>
							<div class="right">
								<button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" type="button" onclick="javascript:adicionaQuestao()">
									<spring:message code="edital.form.dialog.botao.novo.subcriterio"/>
								</button>
							</div>
						</div>						
						<form:errors id="error-nome" path="subcriterios" cssClass="error-block"/>
						<table id="tbSubcriterios" class="wide mdl-data-table mdl-js-data-table mdl-shadow--2dp" path="subcriterios">
						<thead>
							<tr>
								<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.label.codigo"/></th>
								<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.coluna.nome"/></th>
								<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.dialog.label.peso"/></th>
								<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.dialog.label.descricao"/></th>
								<th> </th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="codigo" varStatus="status" items="${form.subcriteriosCodigo}">
						<tr>
							<td class="mdl-data-table__cell--non-numeric alinhamento-codigo">
							    <input name="subcriteriosCodigo" placeholder="AAA" class="editor-peso-prova" pattern="[a-zA-Z]{3}" value="${codigo}"/>
			                    <form:errors cssClass="error-block"/>
							</td>
							<td class="mdl-data-table__cell--non-numeric alinhamento-nome">
							    <input name="subcriteriosNome" class="editor-peso-prova" placeholder="Nome do subcritério." value="${form.subcriteriosNome[status.index]}"/>
			                    <form:errors id="error-subcriterioNome" cssClass="error-block"/>
							</td>
							<td class="alinhamento-peso">
							    <input name='subcriteriosPeso' class="editor-peso-prova" pattern="100|\d{1,2}" value="${form.subcriteriosPeso[status.index]}"/>
			                    <form:errors id="error-subcriterioNome"  cssClass="error-block"/>	
							</td>
							<td class="mdl-textfield mdl-js-textfield alinhamento-descricao">
							    <textarea name="subcriteriosDescricao" class="mdl-textfield__input" type="text" placeholder="Breve descrição do subcritério de alinhamento." rows= "5">${form.subcriteriosDescricao[status.index]}</textarea>
							    <form:errors cssClass="error-block"/>
  							</td>
							<td>
								<c:if test="${status.index > -1}">
									<button type="button" class="mdl-button mdl-js-button mdl-button--icon" onclick="javascript:removeQuestao(event)">
										<i class="material-icons">delete</i>
									</button>
								</c:if>								
							</td>
						</tr>						
						</c:forEach>																	
						</tbody>
						</table>
						
			       	 	<div class="left">
			            </div>
			            <div class="right">
							<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
								<spring:message code="edital.form.botao.salva"/>
							</button>
			            		<a href="${pageContext.request.contextPath}/edital/edit/${form.idEdital}">
								<button type="button" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
									<spring:message code="edital.form.botao.cancela"/>
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
</div>

<script>
function adicionaQuestao() {
	var rawHtml ="<tr>"+
					"<td class='mdl-data-table__cell--non-numeric alinhamento-codigo'>"+
			    		"<input name='subcriteriosCodigo' placeholder='AAA' class='editor-peso-prova' pattern='[a-zA-Z]{3}'>"+
					"</td>"+
					"<td class='mdl-data-table__cell--non-numeric alinhamento-nome'>"+
				    	"<input name='subcriteriosNome' class='editor-peso-prova' placeholder='Nome do subcritério.'>"+
					"</td>"+
					"<td class='alinhamento-peso'>"+
				   		"<input name='subcriteriosPeso' class='editor-peso-prova' pattern='100|\\d{1,2}'>"+
					"</td>"+
					"<td class='mdl-textfield mdl-js-textfield alinhamento-descricao has-placeholder is-dirty is-upgraded' data-upgraded=',MaterialTextfield'>"+
			    		"<textarea name='subcriteriosDescricao' class='mdl-textfield__input' type='text' placeholder='Breve descrição do subcritério de alinhamento.' rows='5'></textarea>"+
					"</td>"+
					"<td>"+
						"<button type='button' class='mdl-button mdl-js-button mdl-button--icon' onclick='javascript:removeQuestao(event)' data-upgraded=',MaterialButton'>"+
							"<i class='material-icons'>delete</i>"+
						"</button>"+
					"</td>"+
					"</tr>"
					//subcriteriosCodigo, subcriteriosNome, subcriteriosPeso, subcriteriosDescricao
	var tr = angular.element(rawHtml);
	
	angular.element(document.querySelector('#tbSubcriterios > tbody'))
		.append(tr);
}

function removeQuestao(event) {
    var target = angular.element(event.target);
    console.log(target.parent().parent().parent().remove());
}
</script>