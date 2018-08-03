package net.cnqisoft.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import net.cnqisoft.util.DBUtil;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class QyListView
 */

public class QueryFacility extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryFacility() {
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
		String company = request.getParameter("company");
		String category = request.getParameter("category");
		String sql = "select * from mm.";
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
		sql += " where item_单位 = (Select id from obpm.t_department where name ='" + company + "')";
		try(
				Connection connection = DBUtil.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
		){
			String json = resultToJson(rs);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 1);
			jsonObject.put("data", new org.json.JSONArray(json));
			response.getWriter().append(jsonObject.toString());
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String resultToJson(ResultSet rs) throws Exception{
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
    	ArrayList list = new ArrayList();
    	Map rowData;
    	int count = 1;
    	while(rs.next()){
	    	rowData = new HashMap(columnCount);
	    	for(int i = 1; i <= columnCount; i++){			
	    		rowData.put(md.getColumnLabel(i), rs.getObject(i));
	    	}
	    	rowData.put("order", count);
	    	count++;
	    	list.add(rowData);   	
    	}
    	
    	JSONArray json = JSONArray.fromObject(list);
    	return json.toString();
	}

}
