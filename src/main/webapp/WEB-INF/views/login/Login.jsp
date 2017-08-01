<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="pt">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Login">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>PPGI/UNIRIO - Login</title>

    <!-- Page styles -->
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" type="text/css" href="https://code.getmdl.io/1.2.1/material.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/third-party/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/third-party/twitter/css/bootstrap-social.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/styles.css">
</head>

<body>
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

        <!-- Header -->
        <header class="mdl-layout__header">
            <div class="mdl-layout__header-row">
                <!-- Title -->
                <span class="mdl-layout-title">
                    PPGI/UNIRIO: Sistema de seleção
                </span>

                <!-- Add spacer, to align navigation to the right -->
                <div class="mdl-layout-spacer"></div>

                <!-- Navigation -->
                <nav class="mdl-navigation">
                    <!-- a class="mdl-navigation__link" href="" style="color:gray">Home</a>
                    <a class="mdl-navigation__link" href="" style="color:gray">About</a -->      
                </nav>
            </div>
        </header>


        <!-- Side bar -->
        <div class="mdl-layout__drawer">
            <span class="mdl-layout-title">
                Sistemas
            </span>
            <nav class="mdl-navigation">
                <a class="mdl-navigation__link" href="coding-piazza/index.html">Coding Piazza</a>
            </nav>
        </div>


        <!-- Central stage -->
        <main class="mdl-layout__content">    
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--3-col graybox">
                </div>

                <div class="mdl-cell mdl-cell--6-col graybox">

                    <sec:authorize access="isAnonymous()">
                        <form action="${pageContext.request.contextPath}/login/authenticate" method="POST" role="form">
                            <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
                                <!-- Add CSRF token -->
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <!-- Form header -->
                                <div class="mdl-card__title">
                                    <h2 class="mdl-card__title-text">
                                    <spring:message code="label.login.form.title"/>
                                    </h2>
                                </div>

                                <!-- Form body -->
                                <div class="mdl-card__supporting-text">

                                <!-- Error message -->
                                <c:if test="${param.error eq 'bad_credentials'}">
                                    <div class="alert alert-danger">
                                        <spring:message code="text.login.page.login.failed.error"/>
                                    </div>
                                </c:if>

                                <!-- E-mail field -->
                                <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <input class="mdl-textfield__input" type="text" name="username" id="user-email">
                                    <label class="mdl-textfield__label" for="user-email">
                                        <spring:message code="label.user.email"/>:
                                    </label>
                                </div>

                                <!-- Password field -->
                                <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <input class="mdl-textfield__input" type="password" name="username" id="user-password">
                                    <label class="mdl-textfield__label" for="user-password">
                                        <spring:message code="label.user.password"/>:
                                    </label>
                                </div>

                                </div>

                                <!-- Form buttons -->
                                <div class="mdl-card__actions mdl-card--border">
                                	<div class="left">
	                                    <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
	                                        <spring:message code="label.user.login.submit.button"/>
	                                    </button>
                                    </div>
                                    <div class="right">
									    <a href="${pageContext.request.contextPath}/login/register" class="mdl-button mdl-button--icon mdl-button--colored">
									    	<i class="material-icons">favorite</i>
									    </a>
									    <a class="mdl-button mdl-button--icon mdl-button--colored"><i class="material-icons">share</i></a>
								    </div>
                                </div>
                            </div>
                        </form>
                        
	                    <!-- a><spring:message code="label.navigation.registration.link"/></a -->

					    <!-- Social Sign In Buttons -->
                        <h2 class="mdl-subtitle-text max-spacer-up">
                      	  <spring:message code="label.social.signin.title"/>
                        </h2>

						<div class="mdl-grid">
		                    <!-- Add Facebook sign in button -->
							<div class="mdl-cell mdl-cell--6-col">
								<a href="${pageContext.request.contextPath}/auth/facebook">
				                    <button class="wide mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect btn-social btn-facebook">
				                        <span class="fa fa-facebook"></span>
				                        <spring:message code="label.facebook.signin.button"/>
				                    </button>
				                </a>
							</div>

		                    <!-- Add Twitter sign in Button -->
							<div class="mdl-cell mdl-cell--6-col">
			                    <a href="${pageContext.request.contextPath}/auth/twitter">
				                    <button href="${pageContext.request.contextPath}/auth/twitter" class="wide mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect btn-social btn-twitter">
				                        <span class="fa fa-twitter"></span>
				                        <spring:message code="label.twitter.signin.button"/>
				                    </button>
				                </a>
							</div>
						</div>
                    </sec:authorize>

					<!-- 
					    If the user is already authenticated, show a help message instead
					    of the login form and social sign in buttons.
					-->
					<sec:authorize access="isAuthenticated()">
					    <p><spring:message code="text.login.page.authenticated.user.help"/></p>
					</sec:authorize>

                </div>

                <div class="mdl-cell mdl-cell--3-col graybox">
                </div>
            </div>
        </main>


        <!-- Footer -->
        <footer class="android-footer mdl-mega-footer">
            <div class="mdl-mega-footer--top-section">
                <div class="mdl-mega-footer--left-section">
                  <p class="mdl-typography--font-light">PPGI/UNIRIO: ©2017</p>
                </div>
                <div class="mdl-mega-footer--right-section">
                  <a class="mdl-typography--font-light" href="#top">
                    Volta para o topo
                    <i class="material-icons">expand_less</i>
                  </a>
                </div>
            </div>
        </footer>

    </div>

    <!-- Material design -->
    <script src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</body>
</html>