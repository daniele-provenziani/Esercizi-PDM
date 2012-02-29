package io.androdi.indovinailnumero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity {
    /** Called when the activity is first created. */
	private Button gioca;
	private EditText io;
	private EditText tu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        gioca=(Button)findViewById(R.id.gioca);
        io=(EditText)findViewById(R.id.io);
        tu=(EditText)findViewById(R.id.tu);
		gioca.setOnClickListener(new OnClickListener() {
			//questa activity permette l'inserimento dei dati del giocatore avversario e dei propri dati
			//poi verranno passati all'activity Main attraverso un intent, a cui abbiamo settato due extra
			//corrispondenti al nome dell'avversario e al nostro con due chiavi IO e TU
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(StartActivity.this,Main.class);
				intent.putExtra("IO", io.getText().toString());
				intent.putExtra("TU", tu.getText().toString());
				startActivity(intent);
				
			}
		});
        
        
    }
}