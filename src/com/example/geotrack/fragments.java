package com.example.geotrack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragments extends Fragment{
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragment_detail,container, false);
	        Log.wtf("INICIALIZANDO", "UNO");
	        return view;
	    }
}
