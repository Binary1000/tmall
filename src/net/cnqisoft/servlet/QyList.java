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
import net.cnqisoft.util.DBUtil;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class QyListView
 */
public class QyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QyList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin","*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		response.setContentType("text/html;charset=utf-8");
		String sql = "select id, item_隐藏, item_通讯地址 from tlk_经营资及文件录入";
		try(
				Connection connection = DBUtil.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
		){
			String json = resultToJson(rs);
			response.getWriter().append(json);
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
    	while(rs.next()){
	    	rowData = new HashMap(columnCount);
	    	for(int i = 1; i <= columnCount; i++){			
	    		rowData.put(md.getColumnName(i), rs.getObject(i));
	    	}
	    	list.add(rowData);	    	
    	}
    	
    	JSONArray json = JSONArray.fromObject(list);
    	return json.toString();
	}

}
