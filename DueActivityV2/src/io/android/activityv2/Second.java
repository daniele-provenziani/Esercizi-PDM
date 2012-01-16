package io.android.activityv2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Second extends Activity
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        TextView label = (TextView)findViewById(R.id.textView1);
        String iltestoricevuto=getIntent().getExtras().getString("iltestonelbox");
        /*ricavo dall'intent attraverso la chiave la stringa digitata dal'utente e
         visualizzo sul Textview con  label.setText(iltestoricevuto);   
         */
        label.setText(iltestoricevuto);   
    }
	
	/*Il codice permette di gestire i Log ovvero tutte le operazioni che l'utente compie durante la sessione
	 * di lavoro, in questo caso attraverso il metodo d() della classe Log e con il tag XML LOG possiamo
	 * verificare la vita della activity second, grazie a dei messaggi da noi settati come START associati al metodo onStart()
	 * La second activity viene creata con il metodo onCreate() che esegue le principali operazioni di inizializzazione,
	 * la seconda actoivity viene in questi caso invocata con un intent dalla prima activity e nel Log si visualizza lo stato 
	 * START(ovvero chiamata metodo onStart() ed è pronta ad essere visualizzata),poi nel Log si visualizza RESUME(ovvero
	 * chiama il metodo onResume() ed l'activity è nello stato che puo interagire con l'utente); se si preme:
	 * -tasto BACK:l'activity passa in stato PAUSA(chiama metodo onPause()),STOP(chiama il metodo onStop()) e infine
	 * 		in DESTROY(chiama il metodo onDestroy()) e l'activity secondaria diventa inattiva
	 * -tasto Home: l'activity passa instato PAUSA,STOP, se riprendiamo l'applicazione l'activity second passa da STOP 
	 * 		a RESTART,START e infine RESUME per rideiventare di nuovo interagibile,  

	 */
	
	@Override
	protected void onStart() 
	{
        super.onStart();
        String str = "START";
        Log.d("XML LOG",str);
	}
	@Override
	protected void onRestart() {
        super.onRestart();
        String str = "RESTART";
        Log.d("XML LOG",str);
        
    }
	@Override
	protected void onResume() {
        super.onResume();
        String str = "RESUME";
        Log.d("XML LOG",str);
    }
	@Override
	protected void onPause() {
        super.onPause();
        String str = "PAUSE";
        Log.d("XML LOG",str);
    }
	@Override
	protected void onStop() {
        super.onStop();
        String str = "STOP";
        Log.d("XML LOG",str);
    }
	@Override
	protected void onDestroy() {
        super.onDestroy();
        String str = "DESTROY";
        Log.d("XML LOG",str);
    }
        

	
}
