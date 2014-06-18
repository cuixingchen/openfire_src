package org.jivesoftware.openfire.plugin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * 数据处理工具类
 * @author cuipengfei
 *
 */

public class DateUtil {

	/**
	 * 生成uuid
	 * @return
	 */
	public static String getUUID(){
		String s=UUID.randomUUID().toString();
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
	
	/**
	 * 通过资源取得用户名
	 * @param resouce
	 * @return
	 */
	public static String getUserName(String resource){
		String username=resource.split("@")[0];
		return username;
	}
	
	/**
	 * java Date转换为数据库Date
	 * @param rq
	 * @return
	 */
	public static Timestamp transDate(Date rq){
		Timestamp t=new Timestamp(rq.getTime());
		return t;
	}
}
