package com.example.geotrack;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.DialogFragment;

/*
 * AboutDialogFragment create a DialogFragment showing information.
 * For using DialogFRagemnt is NECESARY USE MINIMUM API 11
 * 
 */

public class AboutDialogFragment extends DialogFragment {

	/*
	 * Way for making a dialog with specific information.
	 */
	static AboutDialogFragment newInstance() {	
		String info = "Designed by Eduardo Corzo";
        AboutDialogFragment ab = new AboutDialogFragment();
        Bundle args = new Bundle();
        args.putString("inf", info);
        ab.setArguments(args);
        return ab;
    }
	
	/*
	 * Create a Dialog with information.
	 * 
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("inf");
		Dialog myDialog = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setTitle(title).create();
		return myDialog;
	}
	
}
