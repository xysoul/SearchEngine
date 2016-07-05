<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>dblp Search</title>

</head>

<body>

<!-- this is search column -->
<div id="searchbar">
	<table align="center">
		<tr align="center">
			<td>
				<img src="images\\logo.png" width="384" height="150"/>
			</td>
		</tr>
		
		
		<tr align="center">
			<td>
			<form action="${pageContext.request.contextPath}/infoServlet" method="post">
				<input size="80" type="text" name="query" id="query" value="" onkeyup="handleKey()">
				<input type=submit value="search" name="search" id="search">
				<select name="select">
					<option>Author</option>
					<option>Title</option>
					<option>Conference</option>
					<option>Journal</option>			
				</select>
			</form>
			</td>		
		</tr>	
	</table>
</div>



<!-- this is result column -->
<div id="result">

</div>

<!-- this is paging column -->
<div id="paging">

</div>



<!-- this is footer 
<div id="footer">
	<table align="center">
		<tr align="center">
			<td>
				<img src="images/footer.jpg" width="165" height="60"/>
			</td>
		</tr>
	</table>
</div>
-->

</body>
</html>