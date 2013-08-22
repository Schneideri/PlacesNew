package com.places.data;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.GpsStatus.Listener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPlace extends FragmentActivity implements android.content.DialogInterface.OnClickListener
{
	private ImageView imgPic;
	private TextView txtName;
	private TextView txtTags;
	private String sTags;
	private TextView txtText;
	private TextView txtDate;
	private String sImgPath;
	private GoogleMap mapView;
	

	
	protected void onCreateView(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_place);	
		
		//mapController.setCenter(point);
	
		imgPic  = (ImageView)findViewById(R.id.iv_SinglePlace_pic);
		txtName = (TextView)findViewById(R.id.tv_SinglePlace_name);
		txtTags = (TextView)findViewById(R.id.tv_SinglePlace_tags);
		txtText = (TextView)findViewById(R.id.tv_SinglePlace_text);
		txtDate = (TextView)findViewById(R.id.tv_SinglePlace_date);
		
		// MAP
		mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mvMapView)).getMap();
		mapView.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//MapController mapController = mapView.getController();
		
		
		// gets the previously created intent
		Intent thisIntent = getIntent(); 
		
		this.txtName.setText(thisIntent.getStringExtra("extra_Name"));
		this.sTags = thisIntent.getStringExtra("extra_Tags");
		this.txtTags.setText("[ " + sTags + " ]");
		this.txtText.setText(thisIntent.getStringExtra("extra_Text"));
		this.txtDate.setText(thisIntent.getStringExtra("extra_Date"));
		//DEBUG
		this.sImgPath = thisIntent.getStringExtra("extra_ImgPath");
		//this.imgPic.setImageBitmap(BitmapFactory.decodeFile(thisIntent.getStringExtra("extra_ImgPath")));				
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
		return true;
    }
	
	// When menu option is clicked
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
	        case R.id.menu_edit:
	        {
	        	// Loads the Add place layout but with the existing information filled
	        	Intent intentToEdit = new Intent(getApplicationContext(), AddPlace.class);
	        	intentToEdit.putExtra("toEdit", true);
	        	
	        	intentToEdit.putExtra("extra_Name", this.txtName.getText());
	        	intentToEdit.putExtra("extra_Text", this.txtText.getText());
	        	intentToEdit.putExtra("extra_Tags", this.sTags);
	        	intentToEdit.putExtra("extra_Date", this.txtDate.getText());
	        	intentToEdit.putExtra("extra_Path", this.sImgPath);
	        	
	        	// Switching to edit the place layout
	        	startActivity(intentToEdit);
	        	
	        	finish();
				
	            return true;
	        }
	        case R.id.menu_share:
	        {
	        	// Choose a friend to share - if he has Places - sending him a package
	        	// if he doesnt have Places- sending him a  watsup/email message - invitation to use Places
	            Toast.makeText(ViewPlace.this, "Share is Selected", Toast.LENGTH_SHORT).show();
	            return true;
	        }
	        case R.id.menu_delete:
	        {   
	        	// TODO - dialog- r u sure u want to delete: Y/N
	        	// Y: Send a delete command to db
	        	// N: message- not deleted
	        	//Toast.makeText(ViewPlace.this, "Delete is Selected", Toast.LENGTH_SHORT).show();
	        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setMessage("Delete this place perminantly?").setPositiveButton("Yes", this)
	        	.setNegativeButton("No", this).show();
	        	
	        	return true;
	        }
	        
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onClick(DialogInterface dialog, int which) 
	{
		switch (which)
        {
			// If the user is sure to delete
  	        case DialogInterface.BUTTON_POSITIVE:
  	        {
  	        	// Defining DB connection
  				SQL_Connector deletePlaceRequest = new SQL_Connector(getApplicationContext());
  				deletePlaceRequest.open();
  				
  				// bIsNameOk - is true if name is ok (new name) 
  				deletePlaceRequest.DeletePlace(this.txtName.getText().toString());
  				deletePlaceRequest.close();	
  			
  				Toast.makeText(ViewPlace.this, "Place named: " + this.txtName.getText().toString() +
  					  						 	" DELETED", Toast.LENGTH_SHORT).show();
  				
  				this.finish();
  	        
  	            break;
  	        }

  	        case DialogInterface.BUTTON_NEGATIVE:
  	        {
  	        	break;
  	        }
        }
	} 

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	    }
	    
	    return super.onKeyDown(keyCode, event);
	}
}
