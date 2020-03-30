package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	
	 
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
		

		String un=request.getParameter("UserID");
		String pswd1 = request.getParameter("Password");
		String pswd = encode2hex(pswd1);
		
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
		boolean flag = false;
		String rights = null;
		try {
	        //��һ������������
	           Class.forName(DBDRIVER);
	           System.out.println("�ɹ�����!");
	    } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	    }
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			String sql = "select * from Administrator where UserID="+un;
			st = (Statement) conn.createStatement();
			rs = (ResultSet) st.executeQuery(sql);
				
			while(rs.next()){
				if (rs.getString("Password").equals(pswd)){
						flag = true;
						rights=rs.getString("rights");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(flag) {
			 if(rights.equals("super")) {
				 response.sendRedirect("superAD.jsp");
			 }
			 else {
				 response.sendRedirect("normalAD.jsp");
			 }
		 }
		 else {
			 request.setAttribute("message", "Access denied: Either the userID or password are incorrect ");
			 request.getRequestDispatcher("/welcome.jsp").forward(request, response);
//		     JOptionPane.showMessageDialog(null, "Access denied: Either the username and password are incorrect.");
//			 response.sendRedirect("welcome.jsp");
		 } 
	}

	private String encode2hex(String source) {
		// TODO Auto-generated method stub
		byte[] data = encode2bytes(source);
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);

			if (hex.length() == 1) {
				hexString.append('0');
			}

			hexString.append(hex);
		}

		return hexString.toString();
	}

	private byte[] encode2bytes(String source) {
		// TODO Auto-generated method stub
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}
}
