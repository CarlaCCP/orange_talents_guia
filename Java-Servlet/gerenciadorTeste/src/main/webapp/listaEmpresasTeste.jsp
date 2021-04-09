<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="gerenciadorTeste.Empresa"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<c:if test="${ not empty empresa}">
	 	Empresa ${ empresa } cadastrada com sucesso!!!

	</c:if>
<br>
Lista de empresas: <br />

     <ul>
        <c:forEach items="${empresas}" var="empresa">
            <li>${empresa.nome} - <fmt:formatDate value="${empresa.dataAbertura}" 
            pattern="dd/MM/yyyy"/> 
            <a href="/gerenciadorTeste/removeEmpresa?id=${empresa.id}">remover</a>
             <a href="/gerenciadorTeste/mostraEmpresa?id=${empresa.id}">alterar</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>