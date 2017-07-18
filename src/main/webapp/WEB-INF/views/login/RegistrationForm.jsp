<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>

<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
    <div class="page-header">
        <h1><spring:message code="label.user.registration.page.title"/></h1>
    </div>

    <!-- If the user is anonymous (not logged in), show the registration form. -->
    <sec:authorize access="isAnonymous()">
        <div class="panel panel-default">
            <div class="panel-body">
                <form:form action="${pageContext.request.contextPath}/login/create" commandName="user" method="POST" enctype="utf8" role="form">
                    <!-- Add CSRF token to the request. -->
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <!-- If the user is using social sign in, add the provider as a hidden field. -->
                    <c:if test="${user.provider != null}">
                        <form:hidden path="provider"/>
                    </c:if>

                    <!-- Name -->
                    <div class="row">
                        <div id="form-group-name" class="form-group col-lg-4">
                            <label class="control-label" for="user-name"><spring:message code="label.user.name"/>:</label>
                            <form:input id="user-name" path="name" cssClass="form-control"/>
                            <form:errors id="error-name" path="name" cssClass="help-block"/>
                        </div>
                    </div>
                    
                    <!-- Email -->
                    <div class="row">
                        <div id="form-group-email" class="form-group col-lg-4">
                            <label class="control-label" for="user-email"><spring:message code="label.user.email"/>:</label>
                            <form:input id="user-email" path="email" cssClass="form-control"/>
                            <form:errors id="error-email" path="email" cssClass="help-block"/>
                        </div>
                    </div>

                    <!-- Password -->
                    <c:if test="${user.provider == null}">
                        <div class="row">
                            <div id="form-group-password" class="form-group col-lg-4">
                                <label class="control-label" for="user-password"><spring:message code="label.user.password"/>:</label>
                                <form:password id="user-password" path="password" cssClass="form-control"/>
                                <form:errors id="error-password" path="password" cssClass="help-block"/>
                            </div>
                        </div>
                        <div class="row">
                            <div id="form-group-passwordVerification" class="form-group col-lg-4">
                                <label class="control-label" for="user-passwordVerification"><spring:message code="label.user.passwordVerification"/>:</label>
                                <form:password id="user-repeatPassword" path="repeatPassword" cssClass="form-control"/>
                                <form:errors id="error-repeatPassword" path="repeatPassword" cssClass="help-block"/>
                            </div>
                        </div>
                    </c:if>
                    
                    <!-- Submit button -->
                    <button type="submit" class="btn btn-default">
                    	<spring:message code="label.user.registration.submit.button"/>
                    </button>
                </form:form>
            </div>
        </div>
    </sec:authorize>
 
    <!-- If the user is authenticated, show a help message instead of registration form. -->
    <sec:authorize access="isAuthenticated()">
        <p><spring:message code="text.registration.page.authenticated.user.help"/></p>
    </sec:authorize>
</body>
</html>