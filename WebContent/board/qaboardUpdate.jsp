<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<title>QA게시판/수정페이지</title>
<style>
div {
	border: 1px solid #bcbcbc;
	width : 10%
}
</style>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
	<a class="navbar-brand" href="index.jsp">
	 <img src="image/Haitatsu.png" alt="Haitatsu" style="width:100px;">
	</a>

  <ul class="navbar-nav">
    <li class="nav">
   	 <a class="nav-link" href="ybbs_eventList?reqPage=1">이벤트게시판</a>
    </li>
    <c:if test="${users == null}">
	    <li class="nav1">
	    	<a class="nav-link" href="user_login">로그인</a>
	    </li>
    </c:if>
    <c:if test="${users != null}">
		<li class="nav1">
	    	<a class="nav-link" href="user_logout">로그아웃</a>
	    </li>
	</c:if>
  </ul>
</nav>
		<form action="ybbs_update" method="post">
			<div>작성자 :${ybbs.userid}</div>
			<input type="text"  name="qasubject" value="${ybbs.qasubject}" /><br />
			<input type="text"  name="qacomment" value="${ybbs.qacomment}" /><br />
			<input type="hidden"  name="qanumber" value="${ybbs.qanumber}"/><br />
			<input type="submit" class="btn btn-primary" value="수정하기">
		</form>
			<a href="ybbs_detail?qanumber=${ybbs.qanumber}">글로 돌아가기</a>	
	</body>
</html>