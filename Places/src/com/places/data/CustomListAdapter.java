package com.places.data;

import java.util.ArrayList;
import java.util.Locale;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<Place> 
{
	 
    private ArrayList<Place> arrPlacesList;
    private ArrayList<Place> arrayListforFilter;
 
    private LayoutInflater layoutInflater;
 
    public CustomListAdapter(Context context, int layoutResourceId, ArrayList<Place> list_of_Places) 
    { 
    	super(context, layoutResourceId, list_of_Places);
        this.arrPlacesList = list_of_Places;
        this.arrayListforFilter = new ArrayList<Place>();
        this.arrayListforFilter.addAll(arrPlacesList);
        layoutInflater = LayoutInflater.from(context);
    }
 
    @Override
    public int getCount() 
    {
        return arrPlacesList.size();
    }
 
    @Override
    public Place getItem(int position) 
    {
        return arrPlacesList.get(position);
    }
 
    @Override
    public long getItemId(int position) 
    {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        ViewHolder holder;
        if (convertView == null) 
        {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.listview_Name);
            holder.tvTags = (TextView) convertView.findViewById(R.id.listview_Tags);
            holder.imvPhoto = (ImageView) convertView.findViewById(R.id.listview_Photo);

            convertView.setTag(holder);
        } 
        else 
        {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.tvName.setText(((Place)arrPlacesList.get(position)).GetName());
        holder.tvTags.setText(((Place)arrPlacesList.get(position)).GetTags());
        
     	// DEBUG
        holder.imvPhoto.setImageBitmap(
        		(BitmapFactory.decodeFile(((Place)arrPlacesList.get(position)).GetPath())));
 
        return convertView;
    }
 
    static class ViewHolder 
    {
        TextView tvName;
        TextView tvTags;
        ImageView imvPhoto;
    }

    
    // Filter Class displays only places with names like in the sFilterText
   	public void filterNames(String sFilterText) 
	{
		sFilterText = sFilterText.toLowerCase(Locale.getDefault());
		
        arrPlacesList.clear();
        
        if (sFilterText.length() == 0) 
        {
        	arrPlacesList.addAll(arrayListforFilter);
        } 
        else 
        {
            for (Place currPlace : arrayListforFilter) 
            {
                if (currPlace.GetName().toLowerCase(Locale.getDefault())
                        .contains(sFilterText)) 
                {
                	arrPlacesList.add(currPlace);
                }
            }
        }
        
        // Updating the listView
        notifyDataSetChanged();
	}
	
   	// Filter the list using tags search
	public void filterTags(String Tags)
	{
		arrPlacesList.clear();
		
		boolean bMatchesAll = false;
		
		// Separate the words into string array
		String[] arrsSeparatedTags = Tags.split(", ");
		
		// for every place in the view 
		for (Place currPlace : arrayListforFilter) 
        {
			// for every tag in the searched tags
			for (String curTag : arrsSeparatedTags) 
			{
				// does the string of tags contains all of them?
				bMatchesAll = currPlace.GetTags().contains(curTag);
				
				// exit the loop if the current tag does not match
				if(!bMatchesAll) break;		
			}
			
			if(bMatchesAll)
			{
				// yes- add this place to the list of filtered places
				arrPlacesList.add(currPlace);
			}
        }
		
		// Updating the listView
        notifyDataSetChanged();
	}

}
