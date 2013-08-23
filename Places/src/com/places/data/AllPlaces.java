package com.places.data;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class AllPlaces extends ListActivity implements OnItemClickListener, OnClickListener
{	
	private CustomListAdapter adapter;
	private ListView 	lv1;
	private RadioGroup 	rgRadioGroup = null;
	private Button 		bByTags;
	private Tags 		tTags;
	
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		// Setting the view to show_all layout xml
		setContentView(R.layout.show_all);
		
		// Linking the list view - one of the following ways
		//lv1 = (ListView) findViewById(android.R.id.list);
		lv1 = getListView();
		bByTags = (Button) findViewById(R.id.bSearchByTags); 
		bByTags.setOnClickListener(this);	
		
		// Inserting information into the list
		LoadList();  
	}

	 @Override
	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) 
	{
		// DEBUG
		Object o = lv1.getItemAtPosition(pos);
        Place selectedPlace = (Place) o;
      
        Intent inViewChosenPlace = new Intent(v.getContext(), ViewPlace.class);
        
        // Starting a ViewPlace activity
        inViewChosenPlace.putExtra("extra_Name", selectedPlace.GetName());
        inViewChosenPlace.putExtra("extra_Tags", selectedPlace.GetTags());
        inViewChosenPlace.putExtra("extra_Text", selectedPlace.GetText());
        inViewChosenPlace.putExtra("extra_Date", selectedPlace.GetDate());
        
        //DEBUG
        inViewChosenPlace.putExtra("extra_ImgPath", selectedPlace.GetPath());
		
		startActivity(inViewChosenPlace);      
	}

	private void LoadList()
	{
		// Creating connection to SQL
		SQL_Connector info = new SQL_Connector(this);
		info.open();
		
		// Setting the list of places from DB to Arraylist
	 	ArrayList<Place> places_details = new  ArrayList<Place>(info.getData());
	 	
	 	// Closing the DB connection
	 	info.close(); 	
	 	
	 	// Pass results to ListViewAdapter Class
	 	adapter = new CustomListAdapter(this, R.layout.show_all, places_details);
	 	
	 	// Setting the custom list adapter
		lv1.setAdapter(adapter);		
        lv1.setOnItemClickListener(this);  
        
        //--------------the Search option-----------------------------------------------------
        
        // defining search EditText and linking
	    final EditText inputSearch;
	    inputSearch = (EditText) findViewById(R.id.inputSearch);      
	    
	    
	    inputSearch.addTextChangedListener(new TextWatcher() 
	    {
	    	@SuppressWarnings("unchecked")
			@Override
	        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) 
	    	{
	            // When user changed the Text
	    		String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
	    		
	    		// filtering the places list- by the name
	    		adapter.filterNames(text);	
	        }
	         
	        @Override
	        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
	        {} 
	         
	        @Override
	        public void afterTextChanged(Editable arg0) 
	        {}
	    });
	}
	 
	 @Override
	 
	 // when a button clicked
	 public void onClick(View v) 
	 {
		 if(v.getId() == R.id.bSearchByTags)
		 {
			 //tTags = new Tags((LinearLayout) findViewById(R.id.layout_checkboxes));
			 // When user chose to search by tags:
			// Starting the tags dialog for result
			 //showDialog(v);
			 Intent intent = new Intent(v.getContext(),TagsSearchDialog.class);
			 startActivityForResult(intent, 1);	
		}	
	 }
	 
	 // When a called activity (of choosing tags to filter) has announced to be finished- if the result code is OK
	 public void onActivityResult(int requestCode, int resultCode, Intent data) 
	 {
	      if (resultCode == Activity.RESULT_OK) 
	      {
	    	  // Extract the data returned from the child Activity (TagsSearchDialog)
	    	  String sTagsForfilter = data.getStringExtra("extra_checkedtags");
	    	
	    	  // filtering the view of all places by tags
	    	  adapter.filterTags(sTagsForfilter); 
	      } 
	 }
	 
	 // If button back is pressed- finish current activity
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	    }
	    
	    return super.onKeyDown(keyCode, event);
	}
}


