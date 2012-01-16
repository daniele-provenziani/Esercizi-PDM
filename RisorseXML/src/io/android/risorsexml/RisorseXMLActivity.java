package io.android.risorsexml;

import java.io.IOException;
import android.util.Log;
import org.xmlpull.v1.XmlPullParserException;
import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;


public class RisorseXMLActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /* il parser è un processo atto ad analizzare uno stream continuo di input in modo da determinare la sua struttura 
         * grammaticale, il comando getResources().getXml(R.xml.compilation) attinge alla risorsa compilation di tipo xml 
         * grazie al riferimento R.xml.compilation e ne restituisce uno stream di dati */
        XmlResourceParser parser = getResources().getXml(R.xml.compilation);
        try {
        	int eventType = parser.getEventType();//evento puntato dal parser
        	//continuiamo fino alla fine del documento xml
        	while (eventType != XmlResourceParser.END_DOCUMENT) //verifica se siamo alla fine del documento xml
        	{
        		if (eventType == XmlResourceParser.START_TAG) //siamo all'inizio di un tag?es <brano id="1">
        		{//stiamo puntando uno START_TAG 
        			String tagName = parser.getName();//restituisce la Stringa del nome del tag
        			if ("brano".equals(tagName))//stiamo puntanto un brano? 
        			{//si tratta di un tag relativo ad un brano e ne prendiamo l'attributo, in questo caso id="1"
        				String id = parser.getAttributeValue(0);
        				String str = "Brano: " + id;
        				Log.d("XML PARSER", str);//possiamo verificare nel LogCat il valore dell'attributo del tag
        			}
        		}//se non stiamo puntanto un tag puntiamo l'elemento del tag?
        		else if (eventType == XmlResourceParser.TEXT) 
        		{
        			//allora prendiamo il valore dell'elemento in sotto forma di string e lo visualizziamo in log
        			String elementValue = parser.getText();
        			String str = elementValue;
    				Log.d("XML PARSER", str);
        		}
        		//Adiamo avanti nel docuemnto
        		eventType = parser.next();
        	}
        }
        catch (XmlPullParserException e) 
        {
        	e.printStackTrace();
        }
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
    }
}