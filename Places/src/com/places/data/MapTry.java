package com.places.data;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;



public class MapTry extends FragmentActivity
{
		/*private GoogleMap map;
		//private MainFragment fragment;

		@Override
		public void onCreate(Bundle bundle) 
		{
		    super.onCreate(bundle);
		    
		    setContentView(R.layout.maptry);
		    
	        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == 0)
	        {
			    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mvMapView)).getMap();
			    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	        }
		}*/
	
	//getSupportFragmentManager().findFragmentById(R.id.mvMapView))

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maptry);

	    
	    FragmentManager myFM = getSupportFragmentManager();  
	       SupportMapFragment myMAPF = (SupportMapFragment) myFM  
	                 .findFragmentById(R.id.mvMapView);

	       GoogleMap map = myMAPF.getMap();


	    //SupportMapFragment fragment = new SupportMapFragment();
	   /* getSupportFragmentManager().beginTransaction()
	            .add(android.R.id.content, fragment).commit();

	    setContentView(R.layout.activity_main);*/

	    //GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

	    //GoogleMap map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mvMapView)).getMap();
	}

}
