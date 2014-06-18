package org.jivesoftware.openfire.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtil {

	private static final Logger Log = LoggerFactory.getLogger(DbUtil.class);
	
	/**
	 * 插入上线时间
	 * @param id
	 * @param username
	 * @param resource
	 * @param startTime
	 * @param state
	 */
	public static void insert(String id,String username,String resource,Timestamp startTime,byte state){
		String sql="insert into presenceplugin (id,username,source,startTime,state) VALUES (?, ?, ? , ?,?)";
		Log.debug("sql:"+sql);
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, resource);
            pstmt.setTimestamp(4, startTime);
            pstmt.setByte(5, state);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
        	Log.error("sql:"+sql);
        	Log.error("id:"+id);
        	Log.error("username:"+username);
        	Log.error("resource:"+resource);
        	Log.error("startTime:"+startTime);
        	Log.error("state:"+state);
            Log.error(e.getMessage(), e);
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
	}
	
	/**
	 * 查询没有下线的记录
	 * @param username
	 * @param state
	 * @return
	 */
	public static List<HashMap<String,Object>> Query(String username,byte state){
		String sql="select id from presenceplugin where username=? and state=? order by startTime desc ";
		Log.debug("sql:"+sql);
		Connection con = null;
        PreparedStatement pstmt = null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		ResultSet rs = null;
		HashMap<String,Object> map=null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setByte(2, state);
            rs= pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            for (; rs.next();) {
            	map =new HashMap<String,Object>();
                for (int j = 0; j < numCols; j++) {
                	map.put(rsmd.getColumnName(j+1), rs.getObject(j + 1));
                }
                list.add(map);
            }
            return list;
        }
        catch (SQLException e) {
        	Log.error("sql:"+sql);
        	Log.error("state:"+state);
        	Log.error("username:"+username);
            Log.error(e.getMessage(), e);
            return null;
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
	}
	
	/**
	 * 更新对应记录的下线时间和状态
	 * @param id
	 * @param endTime
	 * @param state
	 */
	public static void execute(String id,Timestamp endTime,byte state){
		String sql="update presenceplugin set endTime=?,state=? where id=?";
		Log.debug("sql:"+sql);
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setTimestamp(1, endTime);
            pstmt.setByte(2, state);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
        	Log.error("sql:"+sql);
        	Log.error("id:"+id);
        	Log.error("startTime:"+endTime);
        	Log.error("state:"+state);
            Log.error(e.getMessage(), e);
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
	}
}
