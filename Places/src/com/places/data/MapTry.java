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
	}

}
