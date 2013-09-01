package com.places.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQL_Connector 
{
	private static final String KEY_ROWID = "_id";
	private static final String KEY_NAME = "place_name";
	private static final String KEY_TEXT =  "place_text";
	private static final String KEY_TAGS = "place_tags";
	private static final String KEY_IMG_PATH = "place_img_path";
	private static final String KEY_MAP_POINT = "place_point";
	private static final String KEY_DATE = "place_date";
	
	
	private static final String DB_NAME = "PlacesDB";
	private static final String DB_TABLE = "PlacesTable";
	private static final int DB_VERSION = 1;
	private static String[] columns = new String[] {KEY_ROWID,
													KEY_NAME, 
													KEY_TEXT, 
													KEY_TAGS, 
													KEY_IMG_PATH, 
													KEY_MAP_POINT, 
													KEY_DATE};
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDB;
	
	private static class DbHelper extends SQLiteOpenHelper
	{

		public DbHelper(Context context) 
		{
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			// Called only once to create a DB
			db.execSQL("CREATE TABLE " + DB_TABLE +" (" + 
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_NAME + " TEXT NOT NULL, " +
					KEY_TEXT + " TEXT, " +
					KEY_TAGS + " TEXT, " +
					KEY_MAP_POINT + " TEXT," +
					KEY_IMG_PATH + " TEXT, " +
					KEY_DATE + " TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);	
			onCreate(db);						
		}
		
	}
	
	public SQL_Connector(Context c)
	{
		ourContext = c;
	}
	
	public SQL_Connector open()
	{
		ourHelper = new DbHelper(ourContext);
		ourDB = ourHelper.getWritableDatabase();
		//DeleteCreateTable();	
		return this;
	
	}
	
	public void close()
	{
		// close the helper
		ourHelper.close();
	}

	public long createEntry(String sName, String sText,
			String sTags, String sImgPath, String sMapPoint, String sCurDate) 
	{
		// For the inserted entry
		ContentValues cv = new ContentValues();
		
		// Put information in the content value
		cv.put(KEY_NAME, sName);
		cv.put(KEY_TEXT, sText);
		cv.put(KEY_TAGS, sTags);
		cv.put(KEY_IMG_PATH, sImgPath);
		cv.put(KEY_MAP_POINT, sMapPoint);
		cv.put(KEY_DATE, sCurDate);
		
		// inserting the puts
		// returns the id of the inserted row
		// and -1 if failed
		return ourDB.insert(DB_TABLE, null, cv);
	}

	public List<Place> getData() 
	{
		List<Place> listPlaces = new ArrayList<Place>();
		
		// Getting the cursor for our table and sorting alphabetically
		Cursor c = ourDB.query(DB_TABLE, columns, null, null, null, null, "LOWER(" + KEY_NAME + ")");
		
		// Getting the columns indexes
		int iName = c.getColumnIndex(KEY_NAME);
		int iText = c.getColumnIndex(KEY_TEXT);
		int iTags = c.getColumnIndex(KEY_TAGS);
		int iPath = c.getColumnIndex(KEY_IMG_PATH);
		int iPoint = c.getColumnIndex(KEY_MAP_POINT);
		int iDate = c.getColumnIndex(KEY_DATE);
		
		// Reading every row in the table
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{
			// Create new place sending the context, and the values from the db
			Place pCurr = new Place(this.ourContext, c.getString(iName), 
											   c.getString(iTags),
											   c.getString(iText),
											   c.getString(iPath),
											   c.getString(iPoint),
											   c.getString(iDate));
			
			// Add the current place to the returned list
			listPlaces.add(pCurr);
		}
		
		c.close();
		
		//return listPlaces;
		return listPlaces;
	}

	// Checking if the name does not exist
	// Returns true if it is a new name
	public boolean IsNewName(String sNewName) 
	{
		// Defining the where statement
		String WHERE = KEY_NAME + " LIKE ?";
		
		// Getting the cursor for the selected query
		Cursor cursor = ourDB.query(DB_TABLE, columns, WHERE, new String[] {sNewName}, null, null, null);
		
		int nCount = cursor.getCount();
		
		cursor.close();
		
		// Returns true if the number of rows is 0
		// means new name is ok
		return (nCount == 0);
	}
	
	void DeleteCreateTable()
	{
		ourDB.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		
		// Called only once to create a DB
		ourDB.execSQL("CREATE TABLE " + DB_TABLE +" (" + 
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_NAME + " TEXT NOT NULL, " +
				KEY_TEXT + " TEXT, " +
				KEY_TAGS + " TEXT, " +
				KEY_IMG_PATH + " TEXT, " +
				KEY_MAP_POINT + " TEXT, " +
				KEY_DATE + " TEXT);");
	}
	
	public void DeletePlace(String sName)
	{
		this.ourDB.delete(DB_TABLE, KEY_NAME+ "=?",
		          new String[] { sName});
	}
}
