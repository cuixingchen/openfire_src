package org.jivesoftware.openfire.plugin;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.event.SessionEventDispatcher;
import org.jivesoftware.openfire.event.SessionEventListener;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.PresenceEventDispatcher;
import org.jivesoftware.openfire.user.PresenceEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;

public class StatusPlugin implements Plugin, PresenceEventListener, 
SessionEventListener {
	private static final Logger Log = LoggerFactory.getLogger(StatusPlugin.class);
	
	private void excute(String resouce,byte state ){
		String sql="insert into presenceplugin (id,username,source,stateTime,state) VALUES (?, ?, ? , ?,?)";
		String s=UUID.randomUUID().toString();
		String id=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String rq=df.format(new Date());
		String username=resouce.split("@")[0];
		DbUtil.executeUpdate(sql, id,username,resouce,rq,state );
	}
	
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		PresenceEventDispatcher.addListener(this); 
		Log.info("add  PresenceEventListener ok !"); 
		SessionEventDispatcher.addListener(this); 
		Log.info("add  SessionEventListener ok !"); 
		
	}

	@Override
	public void destroyPlugin() {
		PresenceEventDispatcher.removeListener(this); 
		
	}
	
	@Override
	public void sessionCreated(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(Session session) {
//		Log.info("sessionDestroyed:"+Presence.Type.unavailable.name()); 
//		excute(session.getAddress().toFullJID(),Presence.Type.unavailable.name());
		excute(session.getAddress().toFullJID(),(byte) 0);
		
	}

	@Override
	public void anonymousSessionCreated(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void anonymousSessionDestroyed(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resourceBound(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void availableSession(ClientSession session, Presence presence) {
//		Log.info("availableSession:"+presence.getStatus()); 
		excute(session.getAddress().toFullJID(),(byte)1);
	}

	@Override
	public void unavailableSession(ClientSession session, Presence presence) {
//		Log.info("unavailableSession:"+presence.getStatus()); 
//		excute(session.getAddress().toFullJID(),presence.getStatus());
		
	}

	@Override
	public void presenceChanged(ClientSession session, Presence presence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribedToPresence(JID subscriberJID, JID authorizerJID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribedToPresence(JID unsubscriberJID, JID recipientJID) {
		// TODO Auto-generated method stub
		
	}

	

}
