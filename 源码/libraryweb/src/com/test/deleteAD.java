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
@WebServlet("/deleteAD")
public class deleteAD extends HttpServlet {
	
	 
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
		
		String usr=request.getParameter("UserID");
		
		String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        //���ݿ��ַ
	    String DBURL = "jdbc:sqlserver://localhost:1433;DataBaseName=library";
	        //���ݿ��¼�û���
	    String DBUSER = "julia";
	        //���ݿ��û�����
	    String DBPASSWORD = "sunyu0623";
	        //���ݿ�����
	        //���ݿ����
	    Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
	        //��һ������������
	           Class.forName(DBDRIVER);
	           System.out.println("�ɹ�����!");
	    } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	    }
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			String sql = "delete from Administrator where UserID="+usr;
			
			st = (Statement) conn.createStatement();
			rs = (ResultSet) st.executeQuery(sql);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("Account_List.jsp");
	}

}
