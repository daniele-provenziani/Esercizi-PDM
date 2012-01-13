package io.android.es0;

import android.app.Activity;
import android.os.Bundle;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}

/* @string/hello sta ad indicare che il testo visualizzato in TextView fa riferimento ad
una varibile hello di tipo string presente nella cartella vaules nel file strings.xml, e il 
suo valore puo essere modificato come una comune variabile di tipo string */