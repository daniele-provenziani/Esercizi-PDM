package io.android.dragimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View{

	/*x,y indica il punto dove viene posizionata l'immagine all'esecuzione dell'activity, fa riferimento 
	 * all'angolo in alto a sinistra dell'immagine*/
	private int x=100;
	private int y=100;
	private Bitmap img=null;//classe che gestisce immagini di tipo bmp
	private boolean dragOn=false;
	
	public MyView(Context context) {
		super(context);
		/*Inizializiamo il bitmap e gli associamo una risorsa drawable */
		BitmapFactory.Options opts=new BitmapFactory.Options();
		opts.inJustDecodeBounds=true;
		img=BitmapFactory.decodeResource(context.getResources(), R.drawable.android);
		//si converte in formato bitmap perch  il formato che prende il metodo onDraw che permette di disegnare 
		//la nostra icona
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		/*Il canvans(tela)  attraverso 4 elementi premette di eseguire il processo di movimento dell'imaggine,
		 * i 4 elementi esprimono l'immagine bitmap la posizione x y e il colore che noi abbiamo messo null, se
		 * volevamo cololare dovevamo istanziare un oggetto Paint()*/
		canvas.drawBitmap(img, x, y, null);//viene richiamato dopo un action_move grazie al metodo invalidate()
		//cosi' da aggiornare la grafica
	}
	public boolean onTouchEvent(MotionEvent event)
	{
		//gestiamo l'evento di Touch che viene riportato dal parametro MotionEvent event
		int eventaction =event.getAction();//prendo  il tipo di evento
		int tx=(int)event.getX(); //prendo la posizione X dove c' stato l'evento touch
		int ty=(int)event.getY(); //prendo la posizione Y dove c' stato l'evento touch
		switch(eventaction){
		//verifico che tipo di evento ho e lo gestisco con uno switch
			case MotionEvent.ACTION_DOWN://ho un tipo d'evento ACTION_DOWN, ovvero ho toccato lo schermo
				//verifico se il tocco  all'interno dell'icona
				if(tx>x&tx<x+img.getWidth()&ty>y&ty<y+img.getHeight()){
					dragOn=true;//settando true sono pronto a muovere
				}
				break;//esce dalla switch e ritorna true cosi ho consumato l'evento e sono pronto a gestirne un'altro
			case MotionEvent.ACTION_MOVE:/*ho un tipo d'evento ACTION_DOWN, ovvero dopo che ho toccato lo schermo mi muovo
			su di esso*/
				if(dragOn){/*Se abbiamo avuto un ACTION_DOWN allora la nostra icona verra gestita e si muovera'
				,se non mettevo l'if si muoveva anche se non avevo selezionato l'immagine, */
				x=tx; y=ty;//nuova posizione della nostra icona
				invalidate();/*Il metodo onDraw viene successivamente  chiamato tramite 
				invalidate(). In modo tale si aggiorna la grafica della view, ovvero vedremo il  movimento dell'icona*/
				}
				break;//esce dalla switch e ritorna true cosi ho consumato l'evento e sono pronto a gestirne un'altro
			case MotionEvent.ACTION_UP:/*ho un tipo d'evento ACTION_UP, ovvero tolgo il dito sullo schermo*/
				dragOn=false;//imposto falso cosi la scollego da touch e potro' riverificare di nuovo il touch dentro l'icona
				break;//esce dalla switch e ritorno true nel metodo cosi ho consumato l'evento e sono pronto a gestirne un'altro
		}
		return true;//deve essere consumato l'evento, importante perch cosi gestisco sia il MOVE che il DOWN
		
		
	}
	
	

}
