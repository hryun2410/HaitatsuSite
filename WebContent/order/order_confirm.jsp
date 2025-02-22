<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta content="utf-8">


<style type="text/css">
.table-title {
	color: #fff;
	background: #4b5366;
	padding: 15px 25px;
	margin: -20px -25px 10px;
	border-radius: 3px 3px 0 0;
}

body {
	color: #566787;
	background: #f5f5f5;
	font-family: 'Varela Round', sans-serif;
	font-size: 13px;
}

.table-wrapper {
	background: #fff;
	padding: 20px 25px;
	margin: 30px auto;
	border-radius: 3px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
}

.table-wrapper .btn {
	float: right;
	color: #333;
	background-color: #fff;
	border-radius: 3px;
	border: none;
	outline: none !important;
	margin-left: 10px;
}

.table-wrapper .btn:hover {
	color: #333;
	background: #f2f2f2;
}

.show-entries select.form-control {
	width: 60px;
	margin: 0 5px;
}

table.table tr th, table.table tr td {
	border-color: #e9e9e9;
	padding: 12px 15px;
	vertical-align: middle;
}

table.table tr th:first-child {
	width: 90px;
}

table.table tr th:last-child {
	width: 60px;
}

table.table-striped tbody tr:nth-of-type(odd) {
	background-color: #fcfcfc;
}

table.table-striped.table-hover tbody tr:hover {
	background: #f5f5f5;
}

table.table th i {
	font-size: 13px;
	margin: 0 5px;
	cursor: pointer;
}

table.table td a {
	font-weight: bold;
	color: #566787;
	display: inline-block;
	text-decoration: none;
}

table.table td a:hover {
	color: #2196F3;
}

table.table td a.view {
	width: 30px;
	height: 30px;
	color: #2196F3;
	border: 2px solid;
	border-radius: 30px;
	text-align: center;
}

table.table td a.view i {
	font-size: 22px;
	margin: 2px 0 0 1px;
}

table.table .avatar {
	border-radius: 50%;
	vertical-align: middle;
	margin-right: 10px;
}

.status {
	font-size: px;
	margin: 2px 2px 0 0;
	display: inline-block;
	vertical-align: middle;
	line-height: 10px;
}

.text-success {
	color: #10c469;
}

.text-info {
	color: #62c9e8;
}

.text-warning {
	color: #FFC107;
}

.text-danger {
	color: #ff5b5b;
}

.form-control {
	box-shadow: none;
	border-color: #ddd;
}

.form-control:focus {
	border-color: #4aba70;
}

.login-form {
	width: 350px;
	margin: 0 auto;
	padding: 30px 0;
}

.login-form form {
	color: #434343;
	border-radius: 1px;
	margin-bottom: 15px;
	background: #fff;
	border: 1px solid #f3f3f3;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	padding: 30px;
}

.login-form h4 {
	text-align: center;
	font-size: 22px;
	margin-bottom: 20px;
}

.login-form .avatar {
	color: #fff;
	margin: 0 auto 30px;
	text-align: center;
	width: 100px;
	height: 100px;
	border-radius: 50%;
	z-index: 9;
	background: #fcfffd;
	padding: 0px;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
}

.login-form .avatar i {
	font-size: 62px;
}

.login-form .form-control, .login-form .btn {
	min-height: 40px;
	border-radius: 2px;
	transition: all 0.5s;
}

.login-form .close {
	position: absolute;
	top: 15px;
	right: 15px;
}

.login-form .btn {
	background: #4aba70;
	border: none;
	line-height: normal;
}

.login-form .btn:hover, .login-form .btn:focus {
	background: #42ae68;
}

.login-form .checkbox-inline {
	float: left;
}

.login-form input[type="checkbox"] {
	margin-top: 2px;
}

.login-form .small {
	font-size: 13px;
}

.login-form a {
	color: #4aba70;
}
</style>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://fonts.googleapis.com/css?family=Raleway|Open+Sans"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$("#order_end").click(function() {

			order_end();

			function order_end() {
				var d = '${delivery_check}';
				if (d == 1) {
					location.href = "order_end";
					return false;
				} else if (d == 0) {
					location.href = "mapMove";
					return false;
				}
			}
			;
		});
		$("#back").click(function() {
			history.back();
		});
	});
</script>

</head>
<body>
	<div class="login-form">
		<c:if test="${!empty order_lists}">
			<div class="table-wrapper">
				<div class="table-title">
					<div class="row">
						<div class="col-sm-8">
							<h4>최종 주문리스트</h4>
							<span>>>${order_restaurant}</span>
						</div>
					</div>
				</div>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>주문 메뉴</th>
							<th>갯 수</th>
							<th>가 격</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="o_list" items="${order_lists}">
							<tr>
								<td>${o_list.mName}</td>
								<td>${o_list.count}개</td>
								<td>${o_list.price}원</td>

							</tr>
						</c:forEach>

					</tbody>

				</table>
				<div class="col-sm-8">
					<c:if test="${delivery_check eq 1}">
						<span>총 ${total_price+2000}원 <br />[배달료 2000원 추가]
						</span>
					</c:if>
					<c:if test="${delivery_check eq 0}">
						<span>총 ${total_price}원</span>
					</c:if>
				</div>
				<br /> <input type="button" value="주문 완료하기" id="order_end"
					class="w3-bar-item w3-button w3-red" style="width: 45%"> <input
					type="button" value="뒤로 돌아가기" id="back"
					class="w3-bar-item w3-button w3-red" style="width: 45%">
			</div>
		</c:if>
	</div>
</body>
</html>