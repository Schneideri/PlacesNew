package com.places.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class Tags
{
	// DM:
	private LinearLayout 			layoutCB;
	private ArrayList<CheckBox> 	CBlist;
	public HashMap<String, Boolean> hmTags = 
			new HashMap<String, Boolean>() {{ 	put("ancient", false);	put("animals", false);
												put("art",false);		put("beautiful", false); 																					
												put("children", false);	put("date",	false); 
												put("day", false);		put("faraway", false); 
												put("food", false);		put("friends",false); 
												put("drinks", false);	put("nature", false); 
												put("night", false);	put("party", false); 
												put("private", false); 	put("quiet", false); 
												put("shop", false);		put("water",false);}};
	
	
								
	public Tags(LinearLayout layout)
	{
		this.layoutCB = layout;
		IdentifyCBs();
	}
	
	public String[] GetAllTags()
	{
		return ((String[]) this.hmTags.keySet().toArray());
	}
	
	
		
	//----------------------------------------------------------------------
	// identifying checkboxes in the view
	//----------------------------------------------------------------------
	private void IdentifyCBs()
	{
		// Create new list of the CB
		this.CBlist = new ArrayList<CheckBox>(18);
	    
	    // Going through all of the linear layout children		
 		for(int count = 0; count < layoutCB.getChildCount(); count ++) 
	    {
		   // If the tag of the child is linear layout tagged "tagsvertical"
		   if((layoutCB.getChildAt(count)).getTag().toString().equals("tagsvertical"))
		   {
			   	// get the vertical linear-layout of the checkboxes
		 		LinearLayout layoutCBvertical = ((LinearLayout) layoutCB.getChildAt(count));
		   
			   // getting all of its children
			   for(int counter = 0; counter < layoutCBvertical.getChildCount(); counter ++) 
			    {
				   if((layoutCBvertical.getChildAt(counter)).getTag().toString().equals("cb"))
				   {
					   // Adding the CH to the list
					   CBlist.add((CheckBox) layoutCBvertical.getChildAt(counter));
				   }
			    }
		  	}
	    }
	}
	
	//----------------------------------------------------------------------
	// Using the list of the checkb- marks the checked tags on the map list
	//----------------------------------------------------------------------
	private void UpdateTagsList()
	{ 
		 // for every element in our checkboxes from the view array
		  for (CheckBox checkBox : this.CBlist) 
		  {
			 // if it is checked
			 if(checkBox.isChecked())
			 {
				 // Update the values on the local indication array
				 hmTags.put(checkBox.getText().toString(), true);
			 }
		 }			
	}
	
	public String CheckedTagsToString ()
	{	
		// Updating the checked tags
		UpdateTagsList();
		
		// Preparing the tags string only the ones that checked
		while (hmTags.values().remove(false));

		// Stringing tags
		String sReturned = hmTags.keySet().toString();
		
		// cutting the edges of the tags string [ ] 
		sReturned = sReturned.substring(1, sReturned.length()-1);
		
		return (sReturned);
	}

	public void FromStringToChecked(String sStringTagsToFill) 
	{
		// TODO Auto-generated method stub
		String[] separateTags = sStringTagsToFill.split(", ");
		
		// for every recieved tag string
		for (String tagName : separateTags) 
		{
			// put the true value in the matching key in the hmTags hashmap
			this.hmTags.put(tagName, true);
		}
		
		UpdateChecboxes();
	}
	
	private void UpdateChecboxes()
	{
		// for every element in our checkboxes from the view array
		for (Iterator<CheckBox> iterator = this.CBlist.iterator(); iterator.hasNext();) 
		{
			// for the next CheckBox
			CheckBox currChBox = iterator.next();
			
			// if the matching tag is true - 
			if (this.hmTags.get(currChBox.getText()) == true)
			{
				// Check it
				currChBox.setChecked(true);
			}	
		}
	}
}
