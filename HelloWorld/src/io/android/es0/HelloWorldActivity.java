package io.android.es0;

import android.app.Activity;
import android.os.Bundle;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*Utilizzo la classe R per far riferimento ad una particolare risorsa 
         * contenuta nel documento main.xml e il comando setContetView imposta
         * la nostra interfaccia grafica descritta nel main.xml.
         * La CLASSE R.java è autogenerata nella creazione del progetto e contiene
         * un insieme di costanti intere raggruppate in classi statiche interne associate
         * al particolare tipo di risorsa, e si ulitizza per far riferimento a una particolare 
         * risorsa nella cartella res */
        
    
        								
    }
}

