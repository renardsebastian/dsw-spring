<%@include file="/WEB-INF/views/helper/template.jsp" %>

<script>
var id = "${id}";
</script>

<div id="contents" data-ng-controller="formController as lista">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--12-col page-header">
			<h3><spring:message code="edital.list.title"/></h3>
		</div>
	</div>
	
   	<div class="mdl-grid">
        <div class="mdl-cell mdl-cell--12-col page-header">
	        {{edital.nome}} + {{edital.id}}
	       </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.controller.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/edital/form.dataService.js"></script>
