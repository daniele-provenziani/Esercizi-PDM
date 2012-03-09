package io.esame.pdm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

public class MyView extends View {

	private float[] X;
	private float[] Y;
	private Handler handler = new Handler();
	private int contatore;
	
	Paint paint = new Paint();

	public MyView(Context context, float[] x, float[] y) {
		// TODO Auto-generated constructor stub
		super(context);
		paint.setColor(Color.RED);
		X = x;
		Y = y;

		Runnable runnable = new Runnable() {
			public void run() {
				// IL FOR SERVE PER BLOCCARE IL DISEGNO DELLE LINEE SE ARRIVO
				// ALLA FINE DEI PUNTI
				for (int i = 0; i < X.length-1; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						public void run() {
							invalidate();
							contatore++;
							
						}
					});
				}
			}
		};
		new Thread(runnable).start();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// CODICE PER GENERAZIONE IMMMEDIATA LINEE

		/*
		 * for (int i = 0; i <X.length/2 ; i++) canvas.drawLine(X[i], Y[i], X[i+ 1], Y[i + 1], paint);
		 */
		
		for (int i = 0; i < contatore; i++)
			canvas.drawLine(X[i], Y[i], X[i + 1], Y[i + 1], paint);
	}
}