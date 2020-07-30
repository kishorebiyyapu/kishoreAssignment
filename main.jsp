<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
h1{
color:red;
}
h3{
color:tomato;
}
h2{
color:red;
}


</style>
</head>
<body style="background-color:lightyellow">
	<%@page import="java.sql.DriverManager"%>
	<%@page import="java.sql.ResultSet"%>
	<%@page import="java.sql.Statement"%>
	<%@page import="java.sql.Connection"%>
	<h1 align="center">Online banking</h1>
	<%
		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	%>
	<h3>Transactions</h3>
	<table   border="1" cellpadding="5" cellspacing="0">

		<tr >
			<td>Transaction Id</td>
			<td>Transaction Date</td>
			<td>Credit</td>
			<td>Debit</td>
			<td>Amount</td>
		</tr>



		<%
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "kumar");
				stmt = conn.createStatement();
				stmt1 = conn.createStatement();

				rs = stmt.executeQuery("select * from (select * from detail order by id DESC) where ROWNUM<=5");
				rs1 = stmt1.executeQuery("select * from (select * from detail order by id DESC) where ROWNUM<=1");

				while (rs.next()) {
		%>

		<tr>
			<td><%=rs.getInt(1)%></td>
			<td><%=rs.getDate(2)%></td>
			<td><%=rs.getInt(3)%></td>
			<td><%=rs.getInt(4)%></td>
			<td><%=rs.getInt(5)%></td>

		</tr>
		<%
			}
		%>
	</table>
	<%
		while (rs1.next()) {
			
	%>
	<h2>Total amount:<%=rs1.getInt(5)%></h2>

	<%
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<form action="Transfer" method="post">
		<h3>Money Transfer</h3>
		<table>
			<tr>
				<th style="color:black">Enter amount:</th>
				<td><input type="text" name="amount"></td>
			</tr>
			<tr>
				<td></td>
				<td ><input type="submit" value="Transfer"></td>
			</tr>
		</table>
	</form>
</body>
</html>