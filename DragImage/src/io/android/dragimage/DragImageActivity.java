package io.android.dragimage;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.sax.RootElement;
import android.view.MotionEvent;
import android.widget.Button;

public class DragImageActivity extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*cambio la root del layout,ovvero il layout è gestito dalla classe MyView,
         *  potevo farlo anche nel main con il path esatto*/
        setContentView(new MyView(this));//
       
        
        
       
        
    }
}