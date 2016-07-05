package com.sjtu.searchEngine.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.queryparser.classic.ParseException;
import com.sjtu.core.Info;
import com.sjtu.index.InfoSearch;

public class InfoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{		
		doPost(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		//response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		//HttpSession session = request.getSession();
					
		//得到输入的query作为searchKey
		String query = request.getParameter("query");
		String type = request.getParameter("select");
				
		if(query == null || query.length() == 0){
			out.println("<script>alert('Oh~You forget to input what you want to search~');window.location='index.jsp'</script>");
		}else{
			
			/* test for database query
			ArrayList<Info> list = new ArrayList<Info>();
			String name;
			if(type.equals("Author")){
				Service service = new Service();
				list = service.getAuthorInfoList(query);		
			}*/
					
			//Service service = new Service();
			InfoSearch search = new InfoSearch();
			ArrayList<Info> list = new ArrayList<Info>();
			//ArrayList<Info> list1 = new ArrayList<Info>();
			//ArrayList<Info> list2 = new ArrayList<Info>();
			
			if(type.equals("Author")){
				//list = new ArrayList<Info>();
				try {
					list = search.indexSearchAuthor(query);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}else if(type.equals("Conference")){
				//list = new ArrayList<Info>();
				try {
					list = search.indexSearchConference("confname", query);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if(type.equals("Journal")){
				//list = new ArrayList<Info>();
				try {
					list = search.indexSearchJournal("journalname", query);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if(type.equals("Title")){
				
				try {			
					list = search.indexSearchTitle("titles", query);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
									
//					request.getRequestDispatcher("results.jsp").forward(request, response);
			
					RequestDispatcher rd = request.getRequestDispatcher("/results.jsp");
					request.setAttribute("test", list);
					rd.forward(request, response);
				}
	}
	
	/* test for titles */
	public static void main(String[] args){		
		
		//String query = "Carlos";
		String query = "mobile";
		String type = "Conference";
		
		InfoSearch search = new InfoSearch();
		ArrayList<Info> list = new ArrayList<Info>();
//		if(type.equals("Title")){
//			ArrayList<Info> list;
//			ArrayList<Info> list1;
//			ArrayList<Info> list2;
//			try {
//					list1 = search.indexSearchConference("titles", query);
//					list2 = search.indexSearchJournal("titles", query);
//					list = search.indexSearchAuthor("titles", query);
//					Info info = new Info();
//					
//					for(int i = 0; i < list1.size();i++){
//						info.setName_A(list1.get(i).getName_C());
//						list.add(info);
//					}
//					
//					for(int i = 0; i < list2.size();i++){
//						info.setName_A(list2.get(i).getName_J());
//						list.add(info);
//					}	
//					
//					for(int i = 0; i < list.size();i++){
//						System.out.println("List: name is " + list.get(i).getName_A());
//						System.out.println("=================="+i+"===============");
//					}
//						
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}		
//				
//		}
		
		if(type.equals("Conference")){
			//list = new ArrayList<Info>();
			try {
				list = search.indexSearchConference("confname", query);
				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i).getName_C());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
		

}
