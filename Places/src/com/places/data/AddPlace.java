package com.places.data;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AddPlace extends Activity implements View.OnClickListener
{
	final static int REQ_CODE_CAMERA = 1;
	final static int REQ_CODE_PICK_IMAGE = 2;
	final static int NAME_MAX = 20;
	public static String sSecretFolder = ".secret_places";
	
	private String 		sName;
	private String 		sText;
	private String 		sTags;
	private String 		sImgPath = "not saved";
	private String		sDate;
	private Boolean 	bEditExisting;
	private String		sOldName;
	private Tags 		tAllTags;
	
	private ImageButton imbCamera; 
	private ImageButton imbGallery;
	private ImageView 	ivPreviewPhoto;
	private Button 		btnCheckName;
	private Button 		btnSave;
	private EditText	etName;
	private EditText	etText;
	
	private Intent 		iCameraIntent;
	private Bitmap 		btmPhoto;
	final static int 	iCameraData = 0;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new);
		
		Initialize();		
	}	

	private void Initialize()
	{
		// linking views to dm
		ivPreviewPhoto 	= (ImageView)	findViewById(R.id.imv_Photo);
		imbCamera 		= (ImageButton) findViewById(R.id.imb_Camera);
		imbGallery 		= (ImageButton) findViewById(R.id.imb_Gallery);
		btnCheckName	= (Button) 		findViewById(R.id.b_check_name);
		btnSave			= (Button)	 	findViewById(R.id.b_save_place);
		etName			= (EditText)	findViewById(R.id.et_Name);
		etText			= (EditText)	findViewById(R.id.et_Text);
		
		// Setting listeners to all buttons
		btnCheckName.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		imbCamera.setOnClickListener(this);
		imbGallery.setOnClickListener(this);
		
		// Finding the layout of the checkboxes
		LinearLayout layoutCB = (LinearLayout) findViewById(R.id.layout_checkboxes);
		
		// New tags instance
		tAllTags = new Tags(layoutCB);
		
		// gets the previously created intent
		Intent thisIntent = getIntent(); 
		
		// retrieving whether the user wants to edit the place
		bEditExisting = thisIntent.getBooleanExtra("toEdit", false);
		
		// If it is the first time a place added:
		if(bEditExisting)
		{
			// Getting all from intent extras:
			this.sName 	  = thisIntent.getStringExtra("extra_Name");
			this.sOldName = this.sName;
			this.sImgPath = thisIntent.getStringExtra("extra_Path");
			this.sTags	  = thisIntent.getStringExtra("extra_Tags");
			this.sText 	  = thisIntent.getStringExtra("extra_Text");
			this.sDate = thisIntent.getStringExtra("extra_Date");
			
			// setting all views
			// Name
			this.etName.setText(sName);
		
			// Img
			this.ivPreviewPhoto.setImageBitmap(BitmapFactory.decodeFile(sImgPath));
			
			// Tags
			this.tAllTags.FromStringToChecked(sTags);
			
			// setting old text:
			this.etText.setText(sText);				
		}
		
		//DEBUG
		//EmptyPlacesFolder();
	}

	//----------------------------------------------------------------------
	// Gets and checks the name of the place- returns true if its ok
	//----------------------------------------------------------------------
	private boolean CheckName() 
	{
		// getting the name of the place
		sName = ((EditText)(findViewById(R.id.et_Name))).getText().toString();	
		
		boolean bIsNameOk = false;
		
		// if the name havent been changed
		if(sName.equals(sOldName))
		{
			bIsNameOk = true;
		}
		// check that the name is not null or empty
		else if(!(sName.length() == 0) && (sName.length() < NAME_MAX))
		{
			// Defining DB connection
			SQL_Connector checkNameQuery = new SQL_Connector(this);
			checkNameQuery.open();
			
			// bIsNameOk - is true if name is ok (new name) 
			bIsNameOk = checkNameQuery.IsNewName(sName);
			checkNameQuery.close();	
		}

		// Message to user that the name is wrong
		if (!bIsNameOk)
		{	
			// show toast message
			Toast.makeText
					(AddPlace.this,
					"enter another name",
					Toast.LENGTH_LONG).show();
		}
		else
		{
			// show toast message name ok
			Toast.makeText
				(AddPlace.this,	"Name ok :)",
				Toast.LENGTH_SHORT).show();
		}
		
		return bIsNameOk;
	}

	//----------------------------------------------------------------------
	// In case of any button gets clicked on the add place screen
	//----------------------------------------------------------------------
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			// Case button check name clicked
			case R.id.b_check_name:
			{
				// Getting the place name - entered by use
				CheckName();
				break;
			}
			// Case the camera button clicked
			//DEBUG
			
			case R.id.imb_Camera:
			{
				// defining new camera intent
				iCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(iCameraIntent, REQ_CODE_CAMERA);
				break;
			}
			// Case the gallery button clicked
			case R.id.imb_Gallery:
			{
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);    
				break;
			}
			// Case the save place button clicked
			case R.id.b_save_place:
			{
				// If the name is fine
				if(CheckName())
				{					
					// getting the tags string
					this.sTags = tAllTags.CheckedTagsToString();
					
					// getting the text from text view
					this.sText = ((EditText)(findViewById(R.id.et_Text))).getText().toString();
					
					// Setting the current date
					GetCurDate();
					
					// Saving Place
					Save();
					
					// Starting a ViewPlace activity
					Intent intent = new Intent(this, ViewPlace.class);
					intent.putExtra("extra_Name", sName);
					intent.putExtra("extra_Tags", sTags);
					intent.putExtra("extra_Text", sText);
					intent.putExtra("extra_ImgPath", sImgPath);
					intent.putExtra("extra_Date", sDate);
					
					// After Saving- showing the saved place
					startActivity(intent);	
					
					this.finish();
				}
				
				break;
			}
	
			default:
				break;
		}	
	}

	//----------------------------------------------------------------------
	// Getting and formatting the current date
	//----------------------------------------------------------------------
	private void GetCurDate() 
	{
		// If the user not editing the existing place
		if(!this.bEditExisting)
		{
			// Set up the current date
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
			this.sDate = df.format(c.getTime());
		}
	}

	//----------------------------------------------------------------------
	// Saving the Added place to DB
	//----------------------------------------------------------------------
	private void Save()
	{
		boolean bDidItWork = true;
		
		try
		{
			// Save the chosen Image
			// DEBUG
			SaveImage();
			
            // Creating an instance of SQL connecor class
			SQL_Connector entry = new SQL_Connector(this);					          
			entry.open();
			
			// Deleting the old place
			if(bEditExisting)
			{
				// Saving the place properties in the table
				entry.DeletePlace(this.sOldName);
			}
			
			// Saving the place properties in the table
			entry.createEntry(sName, sText, sTags, sImgPath, sDate);
			
			entry.close();
		}
		catch(Exception e)
		{bDidItWork = false;}
	}

	//----------------------------------------------------------------------
	// Putting the picture taken by camera into the image view on the screen
	//----------------------------------------------------------------------
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
	
		switch(requestCode) 
		{ 
		    case REQ_CODE_CAMERA:
		    {
			    // Check if there is a fine result - operation sucseeded
				if(resultCode == RESULT_OK)
				{
					//DEBUG
					Toast.makeText
					(AddPlace.this,
					"taken picture",
					Toast.LENGTH_LONG).show();
					
					// when the camera activity closes- it puts extras - the photo
					Bundle extras = data.getExtras();
					
					// Updating the bitmap with the extras
					btmPhoto = (Bitmap) extras.get("data");
					
					// Setting the bit map in the image view and creating new path
					ivPreviewPhoto.setImageBitmap(btmPhoto);
				}
				
				break;
			}
				
		    case REQ_CODE_PICK_IMAGE:	
		    {
		    	 // if the user wants to choose from gallery:
		    	 if(resultCode == RESULT_OK)
		    	 {   
	    		 	Uri selectedImage = data.getData();
	                String[] filePathColumn = {MediaStore.Images.Media.DATA};

	                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	                cursor.moveToFirst();

	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                String filePath = cursor.getString(columnIndex);
	                cursor.close();

	                // update the bitmap member
	                btmPhoto = BitmapFactory.decodeFile(filePath);
	                
	                // Setting the bit map in the image view and creating new path
	                ivPreviewPhoto.setImageBitmap(btmPhoto);
		        }
		    	break;
		    }
		    
			default:break;
		}
	}

	// In order to free the saved pictures in the secret folder
	private void EmptyPlacesFolder()
	 {
		// returns folder path :"/data/data/com.places.data/files/.secret_places"
		 File myDir = new File(getFilesDir(), sSecretFolder);
		 
		 //DEBUG
		 // Documents Path 
	    if(myDir.isDirectory())
	    {
	    	// For every child in that directory
           String[] children = myDir.list();
           for (int i = 0; i < children.length; i++) 
           {
        	   // Delete the photo
               new File(myDir, children[i]).delete();     
	       }
	    }	
	}

	private void SaveImage()
	{			
		// attempt1: returns folder path :"/data/data/com.places.data/files/.secret_places"
		 File myDir = new File(getFilesDir(), sSecretFolder);
		 
		 // Setting the image name to the name of the place
		 sImgPath = sName +".jpg";

		 // Documents Path 
	    if(!myDir.exists())
	    {
	    	// this line creates data folder at documents directory
	    	myDir.mkdirs();
	    }	
	    
	    // Create new file
	    File imgFile = new File (myDir, sImgPath);
	    
	    // Replacing if exists
	    if (imgFile.exists ())
	    {
	    	imgFile.delete (); 
	    }
	    
	    try 
	    {
	    	// New file output stream
	    	FileOutputStream out = new FileOutputStream(imgFile);
	           
	        // Compressing bitmap
	        btmPhoto.compress(Bitmap.CompressFormat.JPEG, 70, out);
	        out.flush();
	        out.close();
	        //sImgPath = imgFile.getPath();
	    } 
	    catch (Exception e) 
	    { 
	    	e.printStackTrace();
	    	sImgPath = "not saved";
	    }

	    // DEBUG
       /* Log.d("log", "Image saved to " + sImgPath);
        
        Toast.makeText
		(AddPlace.this,
		"Image saved to " + sImgPath,
		Toast.LENGTH_LONG).show();*/
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

