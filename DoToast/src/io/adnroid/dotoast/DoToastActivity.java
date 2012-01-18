package io.adnroid.dotoast;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DoToastActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	EditText et;/* et e' una variabile di istanza che ci permette di passare la striga edita 
	nell'editext al toast con i metodo et.getText().toString() della classe EditText*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.main); non usiamo il main.xml per impostare la view della activity ma la impostiamo
         * diretamente dal questo file .java attraveso i metodi della classe View specifici per impostare i parametri delle View da aggiungere*/
        LinearLayout ll=new LinearLayout(DoToastActivity.this);//settiamo un layout lineare ceh è gestito dalla nostra classe DoToastActivity
        ll.setOrientation(LinearLayout.VERTICAL);//orientazione layout
        ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));//spazio occupato dal layout
        et=new EditText(this);//il this sta ad indicare che la View dell'activity è gestita dalla stessa classe DoToastActivity
        et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        Button btn=new Button(this);//creo uno bottone con testo Saluta
        btn.setText("Saluta");
        btn.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btn.setOnClickListener(this);//la classe fa da ascoltatore gli eventi sul bottone
        ll.addView(et);//lo metto sopra il bottone nel layout cosi nell'activity comparira' prima del bottone
        ll.addView(btn);
        setContentView(ll); //setto il contenuto della View 
    }
	@Override
	public void onClick(View v) 
	{
		/*Toast (brindisi!!) permette la visualizzazione di messaggi temporanei sul display, il Toast contiene
		 * una testview a cui puo' essere passato il testo esplicito di tipo String o un riferimento sempre per una 
		 * risorsa String, inoltre possiamo impostare la durata della visualizzazione, in questo caso Toast.LENGTH_LONG
		 * un lungo periodo di tempo,per visuallizare bisgona chiamare il metodo show() per visualizzare il toast*/
		Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
		
	}
}