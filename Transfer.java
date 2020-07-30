
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Transfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String amount = request.getParameter("amount");
		int a = Integer.parseInt(amount);
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rst = null;
		int total = 0, f = 0, i = 0, j = 0;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "kumar");

			st = conn.createStatement();
			rst = st.executeQuery("select * from (select * from detail order by id DESC) where ROWNUM<=1");
			while (rst.next()) {
				i = rst.getInt(1);
				total = rst.getInt(5);
			}
			j = i + 1;
			f = total - a;

			ps = conn.prepareStatement("Insert into detail(id,dates,debit,total) values(?,?,?,?)");
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			ps.setInt(1, j);
			ps.setDate(2, sqlDate);
			ps.setInt(3, a);
			ps.setInt(4, f);
			int result = ps.executeUpdate();
			if (result == 1) {
				response.sendRedirect("main.jsp");
			} else {
				response.getWriter().print("Money was not transfered");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
