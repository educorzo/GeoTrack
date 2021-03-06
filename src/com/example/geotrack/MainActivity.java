package com.example.geotrack;


import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.geotrack.MyLocationListener;

public class MainActivity extends Activity {
	private Timer timer;
	private boolean enProgreso; //Control thread
	private boolean killSplash = false; //FLag for closing the Splash
	private Handler handler; //Var for handle the gps
	private LocationManager mlocManager; //Var for handle the gps
	private LocationListener mlocListener; //Var for handle the gps
	private Location loc; //Var for get info about location
	private double lastLongitude = 0; //Var for now the last longitude
	private double lastLatitude = 0; //Var to know the last latitude
	private double MIN = 0.0001; // CONSTANT, minimum distance GPS
	private long TIME =20000; //CONSTANT, time to check the GPS
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		restore(savedInstanceState);
		handler = new Handler();
		/* Start Splash activity */
		Intent intent = new Intent(this, Splash.class);
		startActivityForResult(intent, 1);
		/* Start to get locations */
		threadGPS();
		/*Activate button for fragmentDialog*/
		 Button buttonOpenDialog = (Button)findViewById(R.id.button3);
	        buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					OpenDialog();
				}});
	}
	
	
	/*
	 * Create the dialogFragment
	 */
	 void OpenDialog(){
	    	AboutDialogFragment myDialogFragment = AboutDialogFragment.newInstance();
	    	myDialogFragment.show(getFragmentManager(), "myDialogFragment");
	    }
	 
	 
	/*
	 * Create a thread for check the gps and put info on database It check every
	 * 20 seconds
	 */
	public void threadGPS() {
		enProgreso = true;
		TimerTask tarea = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						checkGPS();
					};
				});
				if (!enProgreso) {
					timer.cancel();// end the thread
				}
			}
		};
		timer = new Timer();
		timer.schedule(tarea, 100, TIME);// open a thread
	}

	/*
	 * Save a position and his date in the Data base
	 */
	public void insertDB(String date, double latitude, double longitude) {
		DataBase usdbh = new DataBase(this, "DB", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();
		if (db != null) {
			String[] args = new String[] { date };
			Cursor c = db.rawQuery("SELECT * FROM Positions WHERE date=?", args);
			if (c.getCount() == 0) {
				db.execSQL("INSERT INTO Positions (date, latitude, longitude) VALUES ('"+ date + "', " + latitude + ", " + longitude + ")");
				Log.wtf("DATABASE", "SAVED POSITION");
			}
			db.close();
		}
	}

	/*
	 * Check the GPS and save the position on the Data Base
	 */
	@SuppressWarnings("deprecation")
	public void checkGPS() {
		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mlocListener);
		loc = new Location("111");
		Location aux = new Location("111");//It is a bad tricky for checking if the loc is really empty
		loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(loc!=null){
			/*If the splash is running, take the information and active the way to close the flash*/
		if ( !loc.equals(aux) && killSplash == false) {
			Log.wtf("Kill", "Splash");
			lastLatitude = loc.getLatitude();
			lastLongitude = loc.getLongitude();
			finishActivity(1);
			killSplash = true;
			Calendar c = Calendar.getInstance();
			Date date=c.getTime();
			insertDB(date.toLocaleString(), loc.getLatitude(), loc.getLongitude());
		}
			/* Get the information, check the distance to the last point and save it on the DB*/
		if (Math.abs(Math.abs(lastLatitude) - Math.abs(loc.getLatitude()) )> MIN || Math.abs(Math.abs(lastLongitude)- Math.abs(loc.getLongitude())) > MIN) {
			Calendar c = Calendar.getInstance();
			Date date=c.getTime();
			Log.wtf("fecha",date.toLocaleString());
			lastLatitude = loc.getLatitude();
			lastLongitude = loc.getLongitude();
			Log.wtf("GPS latitude", "" + lastLatitude);
			Log.wtf("GPS longitude", "" + lastLongitude);
			insertDB(date.toLocaleString(), loc.getLatitude(), loc.getLongitude());
		}
		else{
			Log.wtf("position", "Not necesary");
		}
		}
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
		return true;
	}
	
	
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item){
		 switch (item.getItemId()){
		 
		 case R.id.menu_delete:
			 //Toast.makeText(AndroidMenusActivity.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
			DataBase usdbh = new DataBase(this, "DB", null, 1);
			SQLiteDatabase db = usdbh.getWritableDatabase();
			db.execSQL("DELETE FROM Positions");
			db.close();
			 return true;
		 case R.id.menu_notdelete:
			 return true;
		 default:
	            return super.onOptionsItemSelected(item);
	        }
	    }

	/*
	 * Go to activity Map
	 */
	public void goToMap(View view) {
		Intent intent = new Intent(this, Map.class);
		intent.putExtra("com.example.geotrack.MainActivity", "ALL");
		startActivity(intent);
	}

	/*
	 * Go to activity ListView
	 */
	public void goToList(View view) {
		Intent intent = new Intent(this, ListView.class);
		startActivity(intent);
	}
	
	/*
	 * Close the activity
	 */
	public void killApp(View view) {
		enProgreso=false;
		finish();
	}
	
	
	/*
	 * Restore all the things
	 * I used this method and next method with more data for trining purpose. But it is not necesary for the app 
	 * and now they don't work
	 */
	private void restore(Bundle state) {
		
	}

	/*
	 * 
	 * Save the state
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	
}
