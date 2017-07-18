<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>  
    <title>Homepage</title>  
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css'/>" rel="stylesheet"></link>
  </head>
  
  <body>
    <div class="generic-container">
      <a href="/user/create">Criar Conta</a><br/>
      <a href="/login">Login</a><br/>
    </div>
  </body>
</html>