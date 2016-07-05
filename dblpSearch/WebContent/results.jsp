<%@page import="java.util.ArrayList"%>
<%@page import="com.sjtu.core.Info" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>dblp results</title>

</head>
<body>

<%
	String query = request.getParameter("query");
	String type = request.getParameter("select");
	%>
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
				<input size="80" type="text" name="query" id="query" value="<%=query %>" onkeyup="handleKey()">
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

<hr>


<div id="detail">
<%
	//String query = request.getParameter("query");
	//String type = request.getParameter("select");
	
	//something for dividing pages
	final int pageSize = 15;//每页显示的数量
	int pageCount = 0;//总页数
	int showPage = 1; //当前显示第几页
	
	ArrayList<Info> list = (ArrayList<Info>)request.getAttribute("test");
	if(type.equals("Author")){	
		
		if(list.size() > 0){
			Info info;
			for(int i = 0; i < list.size(); i++){
				info = new Info();
				info = list.get(i);
				out.println("<td ><div align='left'><a href='file:///"+info.getPath()+ "'>" + info.getName_A()+ "</a></td>");
			    out.println("<hr>"); 
			}
		}else{
			out.println("<p>There is no related pages.:(</p>");
		}
		/*
		int num = list.size();
		if(num > 0){
			int recordCount = num;
			//得到总共的页数
			pageCount = (recordCount%pageSize==0)?(recordCount/pageSize):(recordCount/pageSize+1);
			String integer = request.getParameter("showPage");
			if(integer == null){
				integer = "1";
			}
			showPage = Integer.parseInt(integer);
			if(showPage <= 1){
				showPage = 1;
			}
			if(showPage >= pageCount){
				showPage = pageCount;
			}
			System.out.println("showPage:" + showPage);
			System.out.println("pageCount:" + pageCount);
			int position = (showPage-1)*pageSize+1;
			System.out.println("position:"+position);
			
			
			for(int j = position-1; j < position-1+pageSize; j++){
				if(list.size() > 0){
					Info info;
					//for(int i = 0; i < list.size(); i++){
						info = new Info();
						info = list.get(j);
						out.println("<td ><div align='left'><a href='file:///"+info.getPath()+ "'>" + info.getName_A()+ "</a></td>");
					    out.println("<hr>"); 
					//}
				}
				
			}
			
			
		}*/
	}else if(type.equals("Conference")){
		if(list.size() > 0){
			Info info;
			for(int i = 0; i < list.size(); i++){
				info = new Info();
				info = list.get(i);
				//out.println("<td ><div align='left'><a href='"+info.getPath()+ "'>" + info.getName_A()+ "</a></td>"); 
				out.println("<td ><div align='left'><a href='file:///"+info.getPath()+ "'>" + info.getName_C()+ "</a></td>");
				
			    out.println("<hr>"); 
			}
		}else{
			out.println("<p>There is no related pages.:(</p>");
		}
	}else if(type.equals("Journal")){
		if(list.size() > 0){
			Info info;
			
			for(int i = 0; i < list.size(); i++){
				info = new Info();
				info = list.get(i);
				//out.println("<td ><div align='left'><a href='"+info.getPath()+ "'>" + info.getName_A()+ "</a></td>"); 
				
				out.println("<td ><div align='left'><a href='file:///"+info.getPath()+ "'>" + info.getName_J()+ "</a></td>");
				
			    out.println("<hr>"); 
			}
		}else{
			out.println("<p>There is no related pages.:(</p>");
		}
	}else if(type.equals("Title")){
		if(list.size() > 0){
			Info info;
			
			for(int i = 0; i < list.size(); i++){
				info = new Info();
				info = list.get(i);
				//out.println("<td ><div align='left'><a href='"+info.getPath()+ "'>" + info.getName_A()+ "</a></td>"); 
				
				out.println("<td ><div align='left'><a href='file:///"+info.getPath()+ "'>" + info.getName_A()+ "</a></td>");
				
			    out.println("<hr>"); 
			}
		}else{
			out.println("<p>There is no related pages.:(</p>");
		}
	}
	
	
	
	
%>

	
</div>
<!-- 
<br>
  <a href="results.jsp?showPage=<%=showPage-1%>">Previous page</a>
<% //根据pageCount的值显示每一页的数字并附加上相应的超链接
  for(int i=1;i<=pageCount;i++){
 %>
   <a href="results.jsp?showPage=<%=i%>"><%=i%></a>
<% }
 %> 
 <a href="results.jsp?showPage=<%=showPage+1%>">Next page</a>
 <a href="results.jsp?showPage=<%=pageCount%>"> >>|</a>
 -->
</body>
</html>