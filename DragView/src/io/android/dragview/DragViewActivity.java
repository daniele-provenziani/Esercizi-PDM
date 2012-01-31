package io.android.dragview;

import java.security.InvalidAlgorithmParameterException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DragViewActivity extends Activity {
	
	private View selected_item = null;
	private int offset_x = 0;
	private int offset_y = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //prendo il riferimento alla prima immagine
		ImageView larry = (ImageView) findViewById(R.id.imageView1);
		//ascolto l'evento di touch sull'imageview1
		larry.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//verifico se il tipo di evento è un ACTION_DOWN
				if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
					offset_x = (int) event.getX();//setto offset su cui ho toccato la imageview su asse X
					offset_y = (int) event.getY();//setto offset su cui ho toccato la imageview su asse X
					//questo permetto alla view di seguire il movimento del dito da dove ho toccalo la view
					selected_item = v;//setto la view su qui viene l'evento
				}
				return false;//permetto all'evento ACTION_DOWN di riessere notificato, non consumo l'evento
			}	
		});//ascoltiamo l'evento di touch sull'imageview2
		//prendo il riferimento alla seconda immagine
		ImageView van = (ImageView) findViewById(R.id.imageView2);
		van.setOnTouchListener(new View.OnTouchListener() {
			//stessa procedura per la seconda view, quindi sui parametri offset_x,y e sull'oggetto selected_item
			//verranno settati solo per la view su qui avviene il tocco

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
					offset_x = (int) event.getX();
					offset_y = (int) event.getY();
					selected_item = v;
				}
				return false;//permetto all'evento ACTION_DOWN di riessere notificato, non consumo l'evento
			}
		});
		 RelativeLayout r1 = (RelativeLayout) findViewById (R.id.relativeLayout1);
	        r1.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (selected_item == null) return false;//non c'è stata nessuna selezione di una view
					//verifico se dopo aver selezionato l'immagine scorro il dito sullo schermo
					if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
						int x = (int) event.getX() - offset_x;
						int y = (int) event.getY() - offset_y;
						int w = getWindowManager().getDefaultDisplay().getWidth() - 128;
						int h = getWindowManager().getDefaultDisplay().getHeight() - 128;
						if (x > w) x = w;
						if (y > h) y = h;
						//permette di non far rimpicciolire l'immmagine se spostata troppo vicino al limite dello schermo
						//questo perchè l'immagine cerca di rimanere sullo schermo

						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams 
								(new ViewGroup.MarginLayoutParams
										(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT));
				        lp.setMargins(x, y, 0, 0);//sto settando il parametri nel realtivelayout e gli imposto le coordinate
				        //del movimento questo mi serve per far variare i parametri della disposizione della imagineview selezionata
				        //cosi da avere il movimento dell'icona, visto che avviene per ogni tempo dt l'aggiornamento dei parametri
				        selected_item.setLayoutParams(lp);//gli passo i nuovi parametri
				    }if (event.getActionMasked() == MotionEvent.ACTION_UP){
				    	//tolgo il dito dallo schermo e sgancio la view selezionata cosi la posso riselezionare con un nuovo evento touch
						selected_item = null;
					}return true;//consumo l'evento
				}
			});
    }
}
