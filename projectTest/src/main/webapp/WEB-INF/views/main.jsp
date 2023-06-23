<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>테스트 입니다.</h1>
	<button id="dt">의사</button>
	<button id="rn">간호사</button>
	
	<script type="text/javascript">
		$('#dt').click(function(){
			location.href ="/dt/dt-page";
		});
		$('#rn').click(function(){
			location.href ="/rn/rn-page";
		});
	</script>
</body>
</html>