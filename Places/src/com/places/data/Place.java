package com.places.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

// Represents a place instance in the system
// Methods : Ctor
//			 Name to View
//			 Tags to View
//			 Image to View
public class Place
{
	public static int ROW_NUMM= 0;
	private String sName;
	private String sTags;
	private String sText;
	private String sPath;
	private String sMapPoint;
	private String sDate;
	private final Context ourContext;
	private LayoutParams layouParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 
				   												 LayoutParams.WRAP_CONTENT);

	private LayoutParams NameLayout = new LayoutParams( 180,LayoutParams.WRAP_CONTENT, 2);
	private LayoutParams TagsLayout = new LayoutParams(200,LayoutParams.WRAP_CONTENT, 2);
	private LayoutParams PicLayout = new LayoutParams(200,200,1);
	
	
	
	// ctor for place - receiving name tags text and path 
	public Place(Context con, String Name, String Tags, String Text, String Path, String Date, String Point)
	{
		this.ourContext = con;
		this.sName = Name;
		this.sTags = Tags;
		this.sText = Text;
		this.sPath = Path;
		this.sMapPoint = Point;
		this.sDate = Date;
		
		layouParams.setMargins(5, 10, 5, 10);
		NameLayout.setMargins(2 ,5, 2, 5);
		TagsLayout.setMargins(2, 5, 5, 5);
		PicLayout.setMargins(2, 5, 2, 5);
	}
	
	// Properties
	public String GetName()
	{
		return this.sName;
	}
	
	public String GetText()
	{
		return this.sText;
	}
	
	public String GetTags()
	{
		return this.sTags;
	}
	
	public String GetPath()
	{
		return this.sPath; 
	}
	public String GetMapPoint()
	{
		return this.sMapPoint;
	}
	public String GetDate()
	{
		return this.sDate; 
	}
 
    public void setName(String name) 
    {
        this.sName = name;
    }
 
    public void setText(String text) 
    {
        this.sText = text;
    }
 
    public void setTags(String tags) 
    {
        this.sTags = tags;
    }
    public void setMapPoint(String point)
    {
    	this.sMapPoint = point;
    }
	
	// Returns the image view of the image stored in the spesific path
	private ImageView PathToImg ()
	{
		// Decode to bitmap from path in phone memory
		Bitmap myBitmap= BitmapFactory.decodeFile(this.sPath);
		
		// crate image view
		ImageView viewReturned = new ImageView(this.ourContext);
		
		// Set bitmap in the view
		((ImageView)viewReturned).setImageBitmap(myBitmap);
		
		// Cannot be clicked
		viewReturned.setClickable(false);
		
		return viewReturned;
	}
	
	// Text to view
	private TextView NameToView(String Namee) 
	{
		// Creating
		TextView tv = new TextView(this.ourContext);
		
		// Setting ID
		tv.setId((int)System.currentTimeMillis());
		
		// Setting text
		tv.setText(Namee);
		
		tv.setTextColor(Color.rgb(0,98,36));
		tv.setTextSize(15);
		tv.setGravity(Gravity.CENTER);
		
		// Cannot be clicked
		tv.setClickable(false);
		
		return tv;
	}
	
	private TextView TagstoView(String Tagss) 
	{
		// Creating
		TextView tv = new TextView(this.ourContext);
		
		// Setting ID
		tv.setId((int)System.currentTimeMillis());
		
		// Setting text
		tv.setText(Tagss);
		
		tv.setTextColor(Color.rgb(0,125,117));
		tv.setTextSize(15);
		tv.setGravity(Gravity.LEFT);
		
		// Cannot be clicked
		tv.setClickable(false);
		
		return tv;
	}
}
