package com.example.geotrack;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;


public class MyLocationListener  implements LocationListener{
	public double latitude=0;
	public double longitude=0;
	
	/*When there are a challenge save the location.
	 *
	 *
	 */
	@Override
	public void onLocationChanged(Location loc){
		latitude=loc.getLatitude();
		longitude=loc.getLongitude();	
	}	
	
	
	@Override
	public void onProviderDisabled(String provider){
		
		Log.wtf("GPS", "Disable");
	}


	@Override
	public void onProviderEnabled(String provider){
		
		Log.wtf("GPS", "Enable");
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras){

	}

}/* End of Class MyLocationListener */
