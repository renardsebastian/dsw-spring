<%@include file="/WEB-INF/views/helper/template.jsp" %>

<script>
var id = "${id}";
</script>

<div id="contents" data-ng-controller="formController">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--12-col page-header">
			<h3><spring:message code="edital.form.title"/></h3>
		</div>
	</div>
	
   	<div class="mdl-grid">
        <div class="mdl-cell mdl-cell--12-col page-header">
        
        		<!-- Campos básicos -->
			<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
			    <input ng-model="edital.nome" class="mdl-textfield__input" type="text" name="nome" id="nome">
			    <label class="mdl-textfield__label" for="nome">
			    		<spring:message code="edital.form.label.nome"/>:
			    </label>
			</div>

			<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
			    <input ng-model="edital.notaMinimaAlinhamento" class="mdl-textfield__input" type="text" pattern="[0-9]+" name="notaMinima" id="nota-minima-alinhamento">
			    <label class="mdl-textfield__label" for="nota-minima-alinhamento">
			    		<spring:message code="edital.form.label.nota.minima.alinhamento"/>:
			    </label>
			</div>
			
			
			<!-- Provas escritas -->
			<div>
				<div>
					<div class="left">
						Provas Escritas
					</div>
					<div class="right">
						<button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-click="novaProvaEscrita()">
							<spring:message code="edital.form.botao.nova.prova.escrita"/>
						</button>
					</div>
					<div class="clear">
					</div>
				</div>
				
				<table class="wide mdl-data-table mdl-js-data-table mdl-shadow--2dp">
				<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">Código</th>
					<th class="mdl-data-table__cell--non-numeric">Nome</th>
					<th class="mdl-data-table__cell--non-numeric">Disp</th>
					<th>Min</th>
					<th class="mdl-data-table__cell--non-numeric">Pesos</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<tr ng-repeat="prova in edital.provas track by $index">
					<td class="mdl-data-table__cell--non-numeric">{{prova.codigo}}</td>
					<td class="mdl-data-table__cell--non-numeric">{{prova.nome}}</td>
					<td class="mdl-data-table__cell--non-numeric">{{prova.dispensavel ? "S" : "N"}}</td>
					<td>{{prova.notaMinima}}</td>
					<td class="mdl-data-table__cell--non-numeric">{{formataPesos(prova)}}</td>
					<td>
						<button class="mdl-button mdl-js-button mdl-button--icon" confirmed-click="removeProva($index)" ng-confirm-click="<spring:message code='edital.form.message.confirma.remocao.prova'/>">
							<i class="material-icons">delete</i>
						</button>
					</td>
				</tr>
				</tbody>
				</table>
			</div>
			
			
			<!-- Botoes de comando -->
			<div class="block">
				<button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-click="submeteEdital()">
					<spring:message code="edital.form.botao.ok"/>
				</button>
				<button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-click="retornaLista()">
					<spring:message code="edital.form.botao.cancela"/>
				</button>
			</div>
	    </div>
                

		<!-- Janela de edicao de nova prova -->
		<dialog class="mdl-dialog" id="dlgNovaProvaEscrita" style="width: 600px;">
		    <h4 class="mdl-dialog__title">
		    		<spring:message code="edital.form.dialog.nova.prova.titulo"/>
		    </h4>
		    <div class="mdl-dialog__content">
			    	<div>
			    		<div class="left" style="width:25%; padding-right: 16px;">
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
						    <input ng-model="prova.codigo" class="mdl-textfield__input" type="text" name="codigo-prova" id="codigo-prova">
						    <label class="mdl-textfield__label" for="codigo-prova">
						    		<spring:message code="edital.form.dialog.nova.prova.label.codigo"/>:
						    </label>
						</div>
			    		</div>
			    		<div class="right" style="width:70%">
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
						    <input ng-model="prova.nome" class="mdl-textfield__input" type="text" name="nome-prova" id="nome-prova">
						    <label class="mdl-textfield__label" for="nome-prova">
						    		<spring:message code="edital.form.dialog.nova.prova.label.nome"/>:
						    </label>
						</div>
			    		</div>
			    		<div class="clear">
			    		</div>
			    	</div>
			    	
			    	<div>
			    		<div class="left" style="width:25%; padding-right: 16px;">
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label block" mdlTextfieldInput>
						    <input ng-model="prova.notaMinima" class="mdl-textfield__input" type="text" pattern="[0-9]+" name="notaMinima" id="nota-minima-prova">
						    <label class="mdl-textfield__label" for="nota-minima-prova">
						    		<spring:message code="edital.form.dialog.label.nota.minima.prova"/>:
						    </label>
						</div>
			    		</div>
			    		<div class="right" style="width:70%; padding-top: 16px;">
						<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="checkbox-1">
							<input type="checkbox" id="checkbox-1" class="mdl-checkbox__input" checked ng-model="prova.dispensavel">
							<span class="mdl-checkbox__label"><spring:message code="edital.form.dialog.label.dispensavel"/></span>
						</label>
			    		</div>
			    		<div class="clear">
			    		</div>
			    	</div>
				
				<div>
					<div style="margin-top: 16px; margin-bottom: 4px;">
						<div class="left">
							<h4 style="margin: 0px"><spring:message code="edital.form.dialog.label.questoes"/></h4>
						</div>
						<div class="right">
							<button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-click="novaQuestaoProvaEscrita()">
								<spring:message code="edital.form.dialog.botao.nova.questao"/>
							</button>
						</div>
						<div class="clear">
						</div>
					</div>
					<table class="wide mdl-data-table mdl-js-data-table mdl-shadow--2dp">
					<thead>
					<tr>
						<th class="mdl-data-table__cell--non-numeric"><spring:message code="edital.form.dialog.label.peso"/></th>
						<th></th>
					</tr>
					</thead>
					<tbody>
					<tr ng-repeat="peso in prova.questoes track by $index">
						<td class="mdl-data-table__cell--non-numeric">
						    <input ng-model="prova.questoes[$index]" class="editor-peso-prova" type="text" pattern="[0-9]+">
						</td>
						<td>
							<button class="mdl-button mdl-js-button mdl-button--icon" ng-hide="$index == 0" ng-click="removeQuestao($index)">
								<i class="material-icons">delete</i>
							</button>
						</td>
					</tr>
					</tbody>
					</table>
				</div>
		    	</div>
		    <div class="mdl-dialog__actions">
		      	<button type="button" class="mdl-button close">
		      		<spring:message code="template.dialogo.seleciona.edital.botao.cancela"/>
		      	</button>
		      	<button type="button" class="mdl-button" data-ng-click="salvaProva()">
		      		<spring:message code="template.dialogo.seleciona.edital.botao.ok"/>
				</button>
		    </div>
		</dialog>

	    
<!-- 
private List<Usuario> comissaoSelecao;
private List<Usuario> comissaoRecurso;
private List<ProvaEscrita> provasEscritas;
private List<ProjetoPesquisa> projetosPesquisa;
private List<CriterioAlinhamento> criteriosAlinhamento;
 -->	    
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/third-party/ngMaterialLight/ngMaterialLight.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.controller.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.dataService.js"></script>
