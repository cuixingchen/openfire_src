package org.jivesoftware.openfire.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtil {

	private static final Logger Log = LoggerFactory.getLogger(DbUtil.class);
	
	public static void executeUpdate(String sql,String id,String username,String resource,String rq,byte state){
		Log.debug("sql:"+sql);
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, resource);
            pstmt.setString(4, rq);
            pstmt.setByte(5, state);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
        	Log.error("sql:"+sql);
        	Log.error("id:"+id);
        	Log.error("username:"+username);
        	Log.error("propName:"+propName);
        	Log.error("state:"+state);
            Log.error(e.getMessage(), e);
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }
	}
}
