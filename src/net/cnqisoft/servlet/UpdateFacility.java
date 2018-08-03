package net.cnqisoft.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.cnqisoft.util.DBUtil;

/**
 * Servlet implementation class QyListView
 */

public class UpdateFacility extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFacility() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		response.setContentType("text/html;charset=utf-8");
		String category = request.getParameter("category");
		String id = request.getParameter("id");
		String polygonData = request.getParameter("polygondata");
		String sql = "update mm.";
		switch (category) {
			case "堆场设施":
				sql += "tlk_堆场性能";
				break;
			case "道路设施":
				sql += "tlk_道路性能";
				break;
			case "码头设施":
				sql += "tlk_码头性能";
				break;
			case "辅助建筑设施":
				sql += "tlk_辅助生产建筑性能";
				break;
			case "生活设施":
				sql += "tlk_生活设施性能";
				break;
		}
		sql += " set polygondata ='" + polygonData + "' where id = '" + id + "'";
		try(
				Connection connection = DBUtil.getConnection();
				Statement stmt = connection.createStatement();
		){
			stmt.execute(sql);
			response.getWriter().append("{code:1}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
