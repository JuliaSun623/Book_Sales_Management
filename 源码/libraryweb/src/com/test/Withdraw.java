package com.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class register
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String orderID = request.getParameter("orderID");
		
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
		
		try {
	        //��һ������������
	           Class.forName(DBDRIVER);
	           System.out.println("�ɹ�����!");
	    } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	    }
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			String sql = "update order_form set status ='"+"cancel"+"' where orderID = "+orderID;
			st = (Statement) conn.createStatement();
			st.executeQuery(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
			response.sendRedirect("Order_List.jsp");
	}
}
