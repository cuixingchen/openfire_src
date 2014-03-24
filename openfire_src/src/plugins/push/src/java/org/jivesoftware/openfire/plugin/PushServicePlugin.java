package org.jivesoftware.openfire.plugin;

import java.io.File;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

public class PushServicePlugin implements Plugin{

	private static XMPPServer server;
	
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		if(server==null){
			server=XMPPServer.getInstance();
		}
		
	}

	@Override
	public void destroyPlugin() {
		// TODO Auto-generated method stub
		
	}

	public static XMPPServer getXMPPServer(){
		if(server==null){
			server=XMPPServer.getInstance();
		}
		return server;
	}
	
	public static void pushMessage(String to, String from, String body,
			String subject) {
		Message message = new Message();
		message.setFrom(server.createJID(from, null));
		message.setTo(server.createJID(to, null));
		message.setBody(body);
		message.setSubject(subject);
		getXMPPServer().getRoutingTable().routePacket(server.createJID(to, null), message, true);
	}

	
}
