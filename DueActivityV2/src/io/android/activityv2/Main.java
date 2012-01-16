package io.android.activityv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final EditText text=(EditText) findViewById(R.id.editText1);
        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				Intent intent=new Intent(Main.this,Second.class);
				String iltesto= text.getText().toString();
				/*String iltesto= text.getText().toString(); mi permette di
				 * prelevare il testo che l'utente ha digitato nel TextView e lo 
				 * trasforma in una variabile String*/
				intent.putExtra("iltestonelbox", iltesto);
				/*putExtra permette l'associazione  tra un particolare valore, in questo
				 * caso iltesto, e una chiave di tipo String,"iltestonelbox", questo associazione
				 * di infromazioni ad un intent permettera alla activity chiamata con questo intent
				 * di ricavarne il valore associato attraverso la chiave "iltestonelbox",in questo caso
				 * permette alla seconda activity di ricavare il testo inserito nell'EditText dall'utente 
				 * nella prima activity e di farlo visualizzare nella seconda activity nel TextView */
				
				startActivity(intent);//fa partire second activity
				
			}
		});   
    }
}
