package com.test;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class login
 */
@WebServlet("/SearchBook_Author")
public class SearchBook_Author extends HttpServlet {
	
	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<Map> list =new ArrayList<Map>();
		
		String n=request.getParameter("author");
		
		String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        //数据库地址
	    String DBURL = "jdbc:sqlserver://localhost:1433;DataBaseName=library";
	        //数据库登录用户名
	    String DBUSER = "julia";
	        //数据库用户密码
	    String DBPASSWORD = "sunyu0623";
	        //数据库连接
	        //数据库操作
	    Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
	        //第一步，加载驱动
	           Class.forName(DBDRIVER);
	           System.out.println("成功连接!");
	    } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	    }
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			String sql = "select * from bookinfo where author like '%"+n+"%'";
			
			st = (Statement) conn.createStatement();
			rs = (ResultSet) st.executeQuery(sql);
				
			while(rs.next()){
				flag = true;
				String ISBN = rs.getString("ISBN");
				String name = rs.getString("name");
				String press = rs.getString("press");
				String author = rs.getString("author");
				String price = rs.getString("price");
				String store = rs.getString("store");
				
				Map map = new HashMap(); 
				map.put("ISBN", ISBN);
				map.put("name", name);
				map.put("press", press);
				map.put("author", author);
				map.put("price", price);
				map.put("store", store);
				list.add(map);
				for (Map map_1 :list) {
					System.out.println(map_1);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag == true) {
			request.setAttribute("book",list);
			request.getRequestDispatcher("/Search_Book.jsp").forward(request, response);
		}
		else {
			request.setAttribute("book",list);
			request.setAttribute("message", "Fails! There is no such author.");
			request.getRequestDispatcher("/Search_Book.jsp").forward(request, response);
		}
	}

}
