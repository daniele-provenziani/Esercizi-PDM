package io.android.es;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    //onCreate è un metodo che viene chiamato quando l'activity viene inizializzata
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button startButton=(Button) findViewById(R.id.startButton);
        /*Gestisco il bottone attraverso il suo id passato come parametro con R.id.startButton
         * il casting viene fatto perche il metodo restituisce un riferimento tipo View*/
        
        startButton.setOnClickListener(new OnClickListener() 
        {//adesso quando clicco la view botton ho grazie al metodo setOnClickListener ho un callback che mi indica il click
        	@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(Main.this, Second.class));
				/*Serve per lanciara una nuova activity gestita dalla classe Second
				 * quando avviene un click sul bottone da parte dell'utente*/
			}
		});
    }
}