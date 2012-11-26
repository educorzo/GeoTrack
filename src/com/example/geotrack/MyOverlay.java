	package com.example.geotrack;

	import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

	public class MyOverlay extends ItemizedOverlay{

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context mContext;
		
		public MyOverlay (Drawable defaultMarker){
			super(boundCenterBottom(defaultMarker));
		}
		/*
		 * Add OverlayItem to ArrayList
		 */
		public void addOverlay(OverlayItem overlay) {
		    mOverlays.add(overlay);
		    populate();
		}
		/*
		 * Get a Overlay
		 * 
		 */
		@Override
		protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}
		/*
		 * 
		 *GEt the size..
		 */
		@Override
		public int size() {
		  return mOverlays.size();
		}
		
		public MyOverlay(Drawable defaultMarker, Context context) {
			  super(boundCenterBottom(defaultMarker));
			  mContext = context;
			}
		
		@Override
		protected boolean onTap(int index) {
		  OverlayItem item = mOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();
		  return true;
		}
	}
	
