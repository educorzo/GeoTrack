package com.example.geotrack;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class About extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_about, menu);
        return true;
    }
    
    public void goToMain(View view){
    	finish();
    }
}
