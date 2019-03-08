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

	<form action="${pageContext.request.contextPath }/login.do" method="get">
		管理员角色：<br>
		用户名：<input type="text" name="token"value="201617025210">
		<input type="submit" value="登录">
	</form>
	<br/><br/><br/><br/>
	<form action="${pageContext.request.contextPath }/login.do" method="get">
		考生角色：<br>
		用户名：<input type="text" name="token"value="201617025222">
		<input type="submit" value="登录">
	</form>
</body>
</html>