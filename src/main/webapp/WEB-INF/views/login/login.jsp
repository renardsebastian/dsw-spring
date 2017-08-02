<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">    
    <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--3-col">
        </div>

        <div class="mdl-cell mdl-cell--6-col">

            <sec:authorize access="isAnonymous()">
            	<login-form></login-form>
            </sec:authorize>

			<sec:authorize access="isAuthenticated()">
			    <p><spring:message code="text.login.page.authenticated.user.help"/></p>
			</sec:authorize>

        </div>

        <div class="mdl-cell mdl-cell--3-col">
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/components/login/login-form.js"></script>
