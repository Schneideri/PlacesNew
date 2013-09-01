package com.places.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class TagsSearchDialog extends Activity
{	
	private Tags 	tTags;
	private Button bSearch;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        // Setting the content view of the dialog
        setContentView(R.layout.tags_search);
        
        // Initializing the tags Instance
        tTags = new Tags((LinearLayout) findViewById(R.id.layout_checkboxes));
      
        bSearch =  (Button)findViewById(R.id.bSearch_tagsdialog);
        bSearch.setOnClickListener(	new OnClickListener() 
        {
        	// when ok button clicked - adding extras to the result Intent
        	public void onClick(View v) 
        	{
		  		// TODO Auto-generated method stub
        		Intent resultIntent = new Intent();
        		// TODO Add extras or a data URI to this intent as appropriate.
        	
	        	resultIntent.putExtra("extra_checkedtags", tTags.CheckedTagsToString());
	        	setResult(Activity.RESULT_OK, resultIntent);
	        	
	        	finish();
	    	 }
        });
    }
}

