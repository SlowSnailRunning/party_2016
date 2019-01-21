<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 	<h1>${msg }</h1> --%>

	<form action="${pageContext.request.contextPath }/login.do" method="post">
		用户名：<input type="text" name="number"value="21111111112">
		<input type="submit" value="登录">
	</form>
</body>
</html>