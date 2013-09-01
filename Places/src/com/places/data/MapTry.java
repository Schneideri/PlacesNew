package com.places.data;


import android.content.Intent;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapTry extends FragmentActivity implements GoogleMap.OnMapLongClickListener
{
	private static final LatLng ISRAEL = new LatLng(-33.88,151.21);
	private GoogleMap 	map;
	private UiSettings 	uiMap;
	private String 		sPlaceName;
	private LatLng 		llFinalPoint;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    //GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maptry);

	    // getting the name of the place - from extra on the intent
	    sPlaceName = getIntent().getStringExtra("extra_name");
	    
	    // Setting map properties
	   SetMap();	   
	}
	
	// Setting map properties and options
	private void SetMap()
	{
		// Find the map view  
	    SupportMapFragment myMAPF = (SupportMapFragment)getSupportFragmentManager()
	    												.findFragmentById(R.id.the_map);

	    // get the map instance
	    map = myMAPF.getMap();
	    
	    // set the map terrain
	    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	    
	    // Move the camera instantly to Israel with a zoom of 15.
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(ISRAEL, 15));
	    
	    // animate the camera for coolness bonus
	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	    
	    // Getting the ui interface
	    uiMap = map.getUiSettings();
	    
	    // Setting all user gestures enabled - enables the user 
	    // to move the map around zoomin/out
	    uiMap.setAllGesturesEnabled(true);
	    
	    // enabling my location button
	    uiMap.setMyLocationButtonEnabled(true);
	}
	

	// Every time the user long clicks on the map
	@Override
	public void onMapLongClick(LatLng llPoint) 
	{
		map.addMarker(new MarkerOptions()
        .position(llPoint)
        .title(sPlaceName));
		
		this.llFinalPoint = llPoint;
	}

	public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
       // menuInflater.inflate(R.layout.map_menu, menu);
		return true;
    }
	/*
	// When menu option is clicked
 	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       switch (item.getItemId())
        {
        	// in case the edit option pressed 
	        case R.id.menu_save:
	        {
	        	// Loads the Add place layout but with the existing information filled - as extra strings
	        	Intent resultIntent = new Intent();
	        	
	        	// Updating the edit flag on the intent
	        	resultIntent.putExtra("mapPointSet", true);
	        	
	        	resultIntent.putExtra("extra_Point",this.llFinalPoint.toString());
	        	
	        	setResult(Activity.RESULT_OK, resultIntent);
	        	
	        	finish();
				
	            return true;
	        }
	        case R.id.map_menu_cancel:
	        {
	        	Toast.makeText(MapTry.this, "not saved on map", Toast.LENGTH_SHORT).show();
	        	finish();
	            return true;
	        }
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }*/
}
