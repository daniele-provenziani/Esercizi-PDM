package io.android.myplayer;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyPlayerActivity extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer mp;//variabile di istanza che ci serve per gestire il player musicale
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mp=MediaPlayer.create(MyPlayerActivity.this,R.raw.dst);
        /*gli passo il file musicale che e' presenta in res/raw*/
        Button start=(Button) findViewById(R.id.button1);
        start.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) 
			{
        		//quando l'utente clicca su start il mediaplayer fa partire la mussica passata in precedenza
				mp.start();
			}
		});
        Button pause=(Button) findViewById(R.id.button2);
        pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				mp.pause();//mette il playback in pausa
			}
		});     
    }
    protected void onDestroy()
    {
    	/*Quando il sistema invoca il metodo onDestroy() l'acivity diventa inattiva ma se e' in riproduzione la musica nel
    	 * mediaplayer questa continuera' a riprodurla e non possiamo fermarla,senza un meccanismo di rilascio di questa risorsa,
    	 *  che puo' anche portare a consumo della batteria, e il fallimento di riproduzione per le altre applicazioni, allora
    	 * si devo usare il metodo release() che rilascia la risorsa associato all'oggeto MediaPlayer in questo caso mp*/
    	super.onDestroy();
    	mp.release();
    }
}