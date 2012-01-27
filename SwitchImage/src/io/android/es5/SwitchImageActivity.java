package io.android.es5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class SwitchImageActivity extends Activity {
    
	ImageView iv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        iv=(ImageView) findViewById(R.id.imageView1);
        /*Il togglebutton è una particolare View che specializza il CompoundButton
         * ,che permette la possibilità di  essere Checkable e visualizzare lo stato checked(ON)/uncheked(OFF)*/
        ToggleButton tb=(ToggleButton) findViewById(R.id.toggleButton1);
        
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			/*Ascoltiamo evento di checked sul togglebutton*/
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				/*Facciamo l'override del metodo onCheckedChanged e o personaliazaimo per gestire
				 * l'evento, e come parametri il sistema passa l'oggetto CompoundButton(ovvero nel nostro caso tb)
				 * e il valore booleano del stato di checked */
				if(isChecked){//quando isCheched è true ovvero ON è stato premuto il tb
					iv.setImageResource(R.drawable.button_pause);//cambiamo la ImageView e associamo alla risorsa drawable button_pause	
					//ovvero cambio l'immmagine visualizzata in una dove è rappresentanto PAUSE
			}else {
				//il tb è OFF cambiamo la ImageView e associamo alla risorsa drawable button_play
				iv.setImageResource(R.drawable.button_play);
			}
		};
        
    
    });
}
}