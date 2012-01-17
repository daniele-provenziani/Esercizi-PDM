package io.android.esercitazione3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TextDialerActivity extends Activity {
    /** Called when the activity is first created. */
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button digita= (Button) findViewById(R.id.button1);
       
        digita.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				/*Acquisisco il numero tel dal editexte grazio all'id dell'edit 
				 * il testo digitato è trasformato in String*/
				EditText etext= (EditText) findViewById(R.id.editText1);
				String telString=etext.getText().toString();
				String telUriString="tel: "+telString; // tel: 1234567890
				/*Un intent deve specificare su che insieme di dati deve lavorare perchè l'azione di intent
				 * puo assumere significati diversi per tipi di dati diversi, android ci permette di far
				 * specificare il tipo di dato attraverso l'URI, in questo caso all'URI verra' associata
				 *  una stringa,numero telefonico,che con il metodo Uri.parse() diventera' un'istanza della classe Uri*/
				Uri telURI=Uri.parse(telUriString);
				Intent intent=new Intent(Intent.ACTION_DIAL);//creo un intent che faccia visualizzare il numero sul dialer 
				intent.setData(telURI);//il metodo setDATA() permette di passare l'URI all'intent e activity chiamata potrà gestirne i dati
				startActivity(intent);
			}
		});  
        EditText etext=(EditText) findViewById(R.id.editText1);
        /*il testo inserito nell'edit con il metodo addTextChangedListener(new PhoneNumberFormattingTextWatcher())
         * e' un gestore di formattazione per numeri telefonici, ovvero se inseriamo lettere le ignora.
         * Si e' notato che nell'intent che chiama il dialer la formattazione del numero digitato ignora le eventuali lettere
         * digitate nell'editext, invece qundo ho l'intent che avvia la chiamata le lettere non vengono ignorate ma sono trasformate
         * nel numero corrispondente sula tastiera telefonica*/
        etext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        Button chiama= (Button) findViewById(R.id.button2);
        chiama.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				EditText etext= (EditText) findViewById(R.id.editText1);
				String telString=etext.getText().toString();
				String telUriString="tel: "+telString;
				Uri telURI=Uri.parse(telUriString);
				Intent intent=new Intent(Intent.ACTION_CALL);/*Come sopra ma adesso l'azione 
				dell'intent consiste nel far partire activity che fa  direttamente la chiamata con il dato,numero di telefono,
				passato dall'uri.Noi pero' non abbiamo il permesso di fare le chiamate e nel manifest andra' agggiunto
				il relativo permesso che si chiama call_phone...vedi commenti manifest */
				intent.setData(telURI);
				startActivity(intent);
				
			}
			
		});
        
        
        
    }
}