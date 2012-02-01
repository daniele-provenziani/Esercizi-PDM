package io.android.chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity {
	//DEVO AGGIUNGERE IL PERMESSO PER USO DI INTERNET NEL MANIFEST
    /** Called when the activity is first created. */
	private Button send;
	private EditText mess;
	private Connection connection;//variabile di istanza che contiene la connection XMPP
	private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        send=(Button)findViewById(R.id.button1);
        mess=(EditText)findViewById(R.id.editText1);
        tv=(TextView)findViewById(R.id.textView1);
        tv.setMovementMethod(new ScrollingMovementMethod());//abilito lo scrolling sulla textview 
        send.setOnClickListener(new OnClickListener() {
			@Override//
			public void onClick(View v) {
				//aggiunge alla textview il testo passato, cosi facendo visualizzo tutti i messaggi inviati sul display
				tv.append("ME "+ mess.getText().toString()+"\n");
				//spedisco un nuovo messaggio
				Message msg=new Message();
				//La classe Message consente di comunicare informazioni  tra un mittente e un
				//destinatario in una rete
				msg.setTo("ianni@ppl.eln.uniroma2.it");//inserisco destinatario
	        	msg.setBody(mess.getText().toString());//inserisco il payload
	        	connection.sendPacket(msg);//invio il pacchetto	
			}
		});
        try{
        	/*Configuro la connessione imposto il socket del server ppl.eln.uniroma2.it",5222 */
        	ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
        	config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        	connection=new XMPPConnection(config);//setto la connessione ad un server XMPP(costituisce l'insieme dei protocolli di Instant Messaging)
        	connection.connect();
        	connection.login("provenziani","provenziani");//faccio il login al server con nome e password
        	//per funzionare devo improtare solo le classi del package scaricato
        }catch(XMPPException e){
        	e.printStackTrace();
        }
        //ascolto i pacchetti ricevuti(pacchetti in ingresso)
        connection.addPacketListener(new PacketListener() {
			@Override
			public void processPacket(Packet pkt) {
				Message msg =(Message) pkt;//prendo il pacchetto ricevuto e ne faccio il casting in oggetto Message
				String from= msg.getFrom();//acquisisco il mittente
				String body= msg.getBody();//acquisisco il payload
				//aggiunge alla textview il testo passato, cosi facendo visualizzo tutti i messaggi ricevuti sulla texview
				tv.append(from+" : "+body+"\n");

			}
		}, new MessageTypeFilter(Message.Type.normal));	
    }
}