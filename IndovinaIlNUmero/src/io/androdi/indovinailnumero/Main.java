package io.androdi.indovinailnumero;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements MessageReceiver {

	protected static final int SHOW_TOAST = 0;
	//private TextView vedi;
	private String nomeproprio;
	private String nomeavversario;
	private ConnectionManager connection;
	private Stato statoCorrente;
	Timer timer = new Timer();
	private String selectedNumber;
	
	//viene chiamato quando siamo nello stato WAIT_FOR_START_ACK ed aspettiamo STARTACK
	TimerTask sendStart = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (statoCorrente == Stato.WAIT_FOR_START_ACK) {
				connection.send("START");
			} else {
				Log.d("STATO", "Sending START but the state is "
						+ statoCorrente);
			}
		}
	};
	
	
	final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Main.SHOW_TOAST:
				Toast.makeText(Main.this, msg.getData().getString("toast"),//acquisisco il dato con la chiave toast
						Toast.LENGTH_LONG).show();
				break;

			default:
				super.handleMessage(msg);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//vedi = (TextView) findViewById(R.id.vedi);
		nomeproprio = getIntent().getExtras().getString("IO");
		nomeavversario = getIntent().getExtras().getString("TU");
		//istanzio l'oggetto ConnectionManager e al suo construttore gli passo i parametri del nome dei due giocatori
		//l'oggetto ConnectionManager mi instaura la connessione al server xmpp
		connection = new ConnectionManager(nomeproprio, nomeavversario, this);
		//Per devidere chi comincia per primo gestisco la contesa con la lunghezza del nome dei giocatori
		if (nomeavversario.hashCode() < nomeproprio.hashCode()) {
			// inizio io
			timer.schedule(sendStart,1000 , 5000);//programmo la partenza con il ritardo e il periodo di ripetizione
			//imposto come stato della macchina a stati,Stato  una variabile di tipo enumerativa creata da noi
			statoCorrente = Stato.WAIT_FOR_START_ACK;
		} else {
			// Inizia lui Io aspetto il pacchetto
			statoCorrente = Stato.WAIT_FOR_START;
		}

		// vedi.setText(nome);

	}
	//listener sul click dei 3 pulsanti 1 2 3 per selezionare il proprio numero o il numero da selezionare
	public void numberSelected(View v) {
		Button b = (Button) v;
		b.getText().toString();
		if (statoCorrente == Stato.USER_SELECTING) {
			connection.send("SELECTED:" + b.getText().toString());//invio il numero selzionato e il SELECTED
			statoCorrente = Stato.WAIT_FOR_BET;//cambio stato
			
		}else if(statoCorrente==Stato.USER_BETTING){
			String bet =b.getText().toString();
			//verifico se il numero selezionato corrisponde al numero selezionato dall'aversario
			if(bet.equals(selectedNumber)){
				//ho vinto
				connection.send("BET:Y");
				Toast.makeText(Main.this, "BRAVO HAI INDOVINATO",
						Toast.LENGTH_LONG).show();
			}else{//ho perso
				connection.send("BET:N");
				Toast.makeText(Main.this, "PECCATO NON HAI INDOVINATO",
						Toast.LENGTH_LONG).show();
			}
			statoCorrente=Stato.USER_SELECTING;
		}

	}
	//creo una variabile di tipo enumerativa e i rispettivi valori che puo assumere 
	enum Stato {
		WAIT_FOR_START, WAIT_FOR_START_ACK, USER_SELECTING, WAIT_FOR_NUMBER_SELECTION, WAIT_FOR_BET, USER_BETTING
	};

	@Override
	public void receiveMessage(String body) {
		//body contiene il messagio ricevuto dal client dell'avversario
		
		if (body.equals("START")) {//messaggio ricevuto uguale a START(lo invia chi vince la contesa)
			if (statoCorrente == Stato.WAIT_FOR_START) {//sono in stato  WAIT_FOR_START
				// Mando l'ack indietro cosi da notificare lo start
				connection.send("STARTACK");//invio il pacchetto attraverso il metodo send 
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				b.putString("toast", "SCEGLI UN NUMERO");
				osmsg.setData(b);
				handler.sendMessage(osmsg);//mi permette attraverso un Bundle di settare uno string
				//poi chiamando un handler gestisco il messaggio che lo visualizzo come toast
				statoCorrente = Stato.USER_SELECTING;

			} else {//ho ricevuto START ma non mi trovo nello stato giusto
				Log.d("RMessage", "Ricevuto START ma lo stato  "
						+ statoCorrente);

			}
		} else if (body.equals("STARTACK")) {
			if (statoCorrente == Stato.WAIT_FOR_START_ACK) {
				statoCorrente = Stato.WAIT_FOR_NUMBER_SELECTION;//cambio stato
			} else {
				Log.d("RMessage", "Ricevuto STARTACK ma lo stato  "
						+ statoCorrente);

			}
		} else if (body.startsWith("SELECTED")) {
			if (statoCorrente == Stato.WAIT_FOR_NUMBER_SELECTION) {
				selectedNumber = body.split(":")[1];//prelevo il numero selezionato dall'avversario
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				b.putString("toast", "indovina il numero");
				osmsg.setData(b);
				handler.sendMessage(osmsg);
				statoCorrente = Stato.USER_BETTING;
			}else {
				Log.e("RMessage", "Ricevuto SELECTED ma lo stato  "
						+ statoCorrente);
			}

		}else if (body.startsWith("BET")) {
			if (statoCorrente == Stato.WAIT_FOR_BET) {
				String result = body.split(":")[1];
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				if(result.equals("Y")){//il numero selezionato  quello giusto
					b.putString("toast", "Hai perso,il tuo avversario ha indovinato,ora tocca a te");
				}else b.putString("toast", "Hai vinto,il tuo avversario non ha indovinato, ora tocca a te");
				osmsg.setData(b);
				handler.sendMessage(osmsg);//invio il Toast del risultato del BET
				statoCorrente = Stato.WAIT_FOR_NUMBER_SELECTION;//ritorno alla stato di attesa del numero selzionato
			}else {
				Log.e("RMessage", "Ricevuto SELECTED ma lo stato  "
						+ statoCorrente);
			}

	}

}}
