package pdm.test.mappe;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RunnerActivity extends MapActivity {
    /** Called when the activity is first created. */
	private MapView mapView;
	private LocationManager locationManager;
	private MyLocationOverlay myLocationOverlay;
	private RadiusOverlay overlayTermini,overlayPiazzaRep,overlayColosseo,overlayCasaRR;
	private PendingIntent mPeendingTermini;
	private PendingIntent mPeendingPiazzaRep;
	private PendingIntent mPeendingColosseo;
	private PendingIntent mPeendingRR;
	private GeoPoint piazzarep,termini,colosseo,casarr;
	private ProximityBroadcast mProximityBroadcast;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView=(MapView)findViewById(R.id.mapwiev);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
		mapView.setStreetView(true);
        myLocationOverlay=new MyLocationOverlay(this, mapView);
        myLocationOverlay.runOnFirstFix(new Runnable() {
			
			@Override
			public void run() {
				mapView.getController().animateTo(myLocationOverlay.getMyLocation());
			}
		});
        /*Instanzio 4 variabii che identifico con punti(LAT/LON) e raggio(grandezza luogo) e particolare luogo
         * Stazione Termini  		lat:41.901222 	lon:12.500882 	r:400m
         * Piazza della Repubblica  lat:41.902622	lon:12.495482	r:300m
         * Colosseo					lat:41.890310	lan:12.492420	r:500m
         * Casa di Romolo e Remo	lat:41.890492	lan:12.484823	r:450m*/
        
        termini=new GeoPoint(41901222 , 12500882);//invio al costruttore GeoPoint la posizione lat/lon di termini
        overlayTermini=new RadiusOverlay(termini, 400, Color.BLUE);
        //con la classe RadiusOverlay gli passo come parametri al costruttore la posizione e la larghezza del raggio e il colore
        //del layer cosi da creare un cerchio che delimita l'area di termini
        mapView.getOverlays().add(overlayTermini);//inmposto sulla mappa overlay di termini cosi da poter essere visualizzato in layer sopra la mappa
        
        piazzarep=new GeoPoint(41902622 , 12495482);
        overlayPiazzaRep=new RadiusOverlay(piazzarep, 300, Color.MAGENTA);
        mapView.getOverlays().add(overlayPiazzaRep);
        
        colosseo=new GeoPoint(41890310 , 12492420);
        overlayColosseo=new RadiusOverlay(colosseo, 500, Color.RED);
        mapView.getOverlays().add(overlayColosseo);
        
        casarr=new GeoPoint(41890492 , 12484823);
        overlayCasaRR=new RadiusOverlay(casarr, 450, Color.WHITE);
        mapView.getOverlays().add(overlayCasaRR);
        
        
        /*visualizza un pallino corrispondente alla mia posizione attuale, la posizione viene fornita manualmente,
         * visto che si tratta di un emulatore, la possiamo settare nel DDMS.In realtˆ un un dispositivo darebbe la 
         * posizione data dopo fix dei satelliti*/
        mapView.getOverlays().add(myLocationOverlay);
       
    }
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		Intent intentTermini=new Intent("pdm.test.mappe");
		Intent intentPiazzaRep=new Intent("pdm.test.mappe");
		Intent intentColosseo=new Intent("pdm.test.mappe");
		Intent intentRR=new Intent("pdm.test.mappe");

		intentTermini.putExtra("overlay", 1);
		intentPiazzaRep.putExtra("overlay", 2);
		intentColosseo.putExtra("overlay", 3);
		intentRR.putExtra("overlay", 4);
		
		mPeendingTermini=PendingIntent.getBroadcast(this,1, intentTermini,PendingIntent.FLAG_CANCEL_CURRENT);
		mPeendingPiazzaRep=PendingIntent.getBroadcast(this,2, intentPiazzaRep,PendingIntent.FLAG_CANCEL_CURRENT);
		mPeendingColosseo=PendingIntent.getBroadcast(this,3, intentColosseo,PendingIntent.FLAG_CANCEL_CURRENT);
		mPeendingRR=PendingIntent.getBroadcast(this,4, intentRR,PendingIntent.FLAG_CANCEL_CURRENT);
		 
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
		
		locationManager.addProximityAlert(termini.getLatitudeE6()*0.000001,termini.getLongitudeE6()*0.000001,400, 
				-1, mPeendingTermini);
		locationManager.addProximityAlert(piazzarep.getLatitudeE6()*0.000001, piazzarep.getLongitudeE6()*0.000001,400, 
				-1, mPeendingPiazzaRep);
		locationManager.addProximityAlert(colosseo.getLatitudeE6()*0.000001, colosseo.getLongitudeE6()*0.000001,400, 
				-1, mPeendingColosseo);
		locationManager.addProximityAlert(casarr.getLatitudeE6()*0.000001, casarr.getLongitudeE6()*0.000001,400, 
				-1, mPeendingRR);
		
		registerReceiver(mProximityBroadcast, new IntentFilter("pdm.test.mappe"));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		unregisterReceiver(mProximityBroadcast);
		LocationManager locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
		locationManager.removeProximityAlert(mPeendingTermini);
		locationManager.removeProximityAlert(mPeendingColosseo);
		locationManager.removeProximityAlert(mPeendingPiazzaRep);
		locationManager.removeProximityAlert(mPeendingRR);
	}
	
class ProximityBroadcast extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			int area=arg1.getIntExtra("overlay", -1);//raccolgo l'intent
			boolean stoEntrando=arg1.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,true);
			if (stoEntrando)//se sono in prossimita delle aree delimitate verifico il valore dell'extra dell'intent cosi da
				//cominucare in quale area stiamo entrando attraverso i toast
			{
				if(area==1){
					Toast.makeText(getApplicationContext(), "Benvenuto alla stazione Termini", Toast.LENGTH_LONG).show();
				}
				if(area==2){
					Toast.makeText(getApplicationContext(), "Benvenuto in piazza della Repubblica", Toast.LENGTH_LONG).show();
				}
				if(area==3){
					Toast.makeText(getApplicationContext(), "Benvenuto al Colosseo", Toast.LENGTH_LONG).show();
				}
				if(area==4){
					Toast.makeText(getApplicationContext(), "Benvenuto alla casa di Romolo e Remo", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "ARRIVEDERCI", Toast.LENGTH_LONG).show();
			}
			Log.d("BC", "Allarme Prossimitˆ");
			//Toast.makeText(getApplicationContext(), "Alert di prossimitˆ", Toast.LENGTH_LONG).show();
		}
		
	}
}