package io.esame.pdm;


import android.app.Activity;
import android.os.Bundle;



public class TracciaLineeMain extends Activity {
    /** Called when the activity is first created. */
	float [] X=new float[5000];
	float [] Y=new float [5000];
	//private TextView vedi;
	/*Timer timer = new Timer();
	TimerTask sendStart = new TimerTask() {
		@Override
		public void run() {
			
			assegnoXY((float)Math.random()*maxW,(float)Math.random()*maxH-10);
			i++
		
		}
	};*/
	float maxH=0;
	float maxW=0;
	int i=0;
	//private StringBuilder buffer = new StringBuilder();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        //vedi= (TextView)findViewById(R.id.textView1);
     	 maxH=getWindowManager().getDefaultDisplay().getHeight();
         maxW=getWindowManager().getDefaultDisplay().getWidth();
        //timer.schedule(sendStart,1000 , 5000);
       
        for(i=0;i<10;i++)
        assegnoXY((float)Math.random()*maxW+1,(float)Math.random()*maxH+1);
         setContentView(new MyView(this,X,Y));//gli passo alla activity il context dove  definita la mia view
    }
    
	public void assegnoXY(float x,float y)
    {
    	X[i]=x;
    	Y[i]=y;
    	//Textview per visualizzare i numeri casuali
    	//buffer.append(X[i]).append("   ").append(Y[i]).append("\n");
    	//vedi.setText(buffer);
    }
	
	
   
    
}