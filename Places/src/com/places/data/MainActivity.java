package com.places.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// define the buttons from the xml
		Button btnAddPlace = (Button) findViewById(R.id.b_add_place);
		Button btnFindPlace = (Button) findViewById(R.id.b_find_place);
		
		btnAddPlace.setOnClickListener(this);
		btnFindPlace.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.b_add_place:
			{
				Intent intent = new Intent(v.getContext(), AddPlace.class);
				startActivityForResult(intent, 0);	
				break;
			}
			case R.id.b_find_place:
			{
				Intent intent = new Intent(v.getContext(), MapTry.class);
				startActivity(intent);
				break;
			}
		}		
	}
}
