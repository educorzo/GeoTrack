package com.example.geotrack;

import java.util.List;

import com.example.geotrack.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

public class Map extends MapActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		MapView mapView = (MapView) findViewById(R.id.mapa);
		mapView.setBuiltInZoomControls(true);
		/* DRAW SECTION */
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.positton);// Change the icon
		MyOverlay itemizedoverlay = new MyOverlay(drawable, this);
		/* INTERACTION WITH THE DATA BASE */
		DataBase usdbh = new DataBase(this, "DB", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();
		if (db != null) {
			if (this.getIntent().getStringExtra(
					"com.example.geotrack.MainActivity") != null) {
				// PUT ALL THE POINTS
				String[] args = new String[] { "1" };
				Cursor c = db.rawQuery("SELECT * FROM Positions WHERE '1'=?",
						args);
				int latitudeE = 0;
				int longitudeN = 0;
				if (c.moveToFirst()) {
					latitudeE = (int) c.getDouble(1);
					longitudeN = (int) c.getDouble(2);
					GeoPoint point = new GeoPoint(latitudeE, longitudeN);
					OverlayItem overlayitem = new OverlayItem(point,
							c.getString(0), "" + latitudeE + " " + longitudeN);
					itemizedoverlay.addOverlay(overlayitem);
					while (c.moveToNext()) {
						latitudeE = (int) c.getDouble(1);
						longitudeN = (int) c.getDouble(2);
						GeoPoint point1 = new GeoPoint(latitudeE, longitudeN);
						OverlayItem overlayitem1 = new OverlayItem(point1,
								c.getString(0), "" + latitudeE + " "
										+ longitudeN);
						itemizedoverlay.addOverlay(overlayitem1);
					}
					mapOverlays.add(itemizedoverlay);
				}

			} else if (this.getIntent().getStringExtra("com.example.geotrack.ListView") != null) {
				// SHOW ONLY ONE POINT
				String aux = this.getIntent().getStringExtra("com.example.geotrack.ListView");
				String[] args = new String[] { aux };
				Cursor c = db.rawQuery("SELECT * FROM Positions WHERE date=?",args);
				int latitudeE = 0;
				int longitudeN = 0;
				if (c.moveToFirst()) {
					latitudeE = (int) c.getDouble(1);
					longitudeN = (int) c.getDouble(2);
				}
				GeoPoint point = new GeoPoint(latitudeE, longitudeN);
				OverlayItem overlayitem = new OverlayItem(point,c.getString(0), "" + latitudeE + " " + longitudeN);
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);
			}
			db.close();
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	public void goToMenu(View view) {
		finish();
	}

}
