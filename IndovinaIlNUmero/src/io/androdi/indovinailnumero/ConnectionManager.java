package io.androdi.indovinailnumero;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;

public class ConnectionManager implements PacketListener {

	public String TAG = "ppl.connection.manager";
	private boolean connected=false;
	private Connection connection;
	private String nomeMio, nomeAvversario;
	private MessageReceiver mr;//estenndo l'interfaccia da me creata in cui il metodo si trova nella classe main

	public ConnectionManager(String nomeMio, String nomeAvversario,MessageReceiver mr) {
		super();
		this.nomeMio = nomeMio;
		this.nomeAvversario = nomeAvversario + "@ppl.eln.uniroma2.it";
		this.mr = mr;
		//CONFIGURO LA CONNESSIONE AL SERVER XMPP
		try {
			ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it", 5222);//imposto il socket
			config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			connection = new XMPPConnection(config);
			connection.connect();
			//gli passo username e password al server
			connection.login(nomeMio, nomeMio);
			connection.addPacketListener(this, new MessageTypeFilter(Message.Type.normal));//ascolto i pacchetti ricevuti(pacchetti in ingresso)
			//il this sta ad indicare che  il metodo processPacket è in questa classe
			connected = true;
			Log.d(TAG, "XMPP Connection Started");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	//gestisco i pacchetti ricevuti
	@Override
	public void processPacket(Packet pkt) {
		Message msg = (Message) pkt;//prendo il pacchetto ricevuto e ne faccio il casting in oggetto Message
		Log.d(TAG, "MSG RECV from:" + msg.getFrom() + " BODY:" + msg.getBody());
		if (msg.getFrom().startsWith(nomeMio)) {
			//il messaggio è scartato
			Log.d(TAG, "MSG DISCARDED coming from " + msg.getFrom()
					+ "with body " + msg.getBody() + " myuser:" + nomeMio);
		} else {
			mr.receiveMessage(msg.getBody());//chiamo metodo nella classe main
		}
	}

	public void send(String body) {
		Message msg = new Message();
		msg.setTo(nomeAvversario);
		msg.setBody(body);
		Log.d(TAG, "MSG SENT to:" + msg.getTo() + " BODY:" + msg.getBody());
		connection.sendPacket(msg);
	}
	
	public void close(){
		connection.disconnect();
	}

}
