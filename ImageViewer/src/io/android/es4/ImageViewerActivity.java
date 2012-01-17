package io.android.es4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewerActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView image=(ImageView) findViewById(R.id.imageView1);//setto un immagine per l'icona dell'app e poi ne punto l'id
        image.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM));
        /*Ho importato nella sdcard un immagine, se la visualiziamo in galleria possiamo decidere di inviarla(share),
         * come messagio oppure scegliere la nostra app ImageViewer, scegliendo la nostra app per gestire l'intent di Send la 
         * visualizza nell'activity grazie a questo codice image.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM)) 
         * che preleva URI dell'intent che ha chiamato il send, e classe image viene settata l'immagine passata nell'uri, ovvero quella
         * visualizzata nella gallery e la visualizza,infatti senza questo codice l'activity visualizzerebe l'imagine del'icona
         * puntata da R.id.imageView1*/
    }
}