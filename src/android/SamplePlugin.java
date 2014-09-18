package de.michaelskoehler.sampleplugin;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.content.Context;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.Activity;


public class SamplePlugin extends CordovaPlugin {
    public static final String ACTION_SHOW_ALERT_DIALOG = "showAlertDialog";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
        	
        	// Case: showAlertDialog action
            if (ACTION_SHOW_ALERT_DIALOG.equals(action)) { 
            	
            	// Fetch arguments
            	JSONObject arg_object = args.getJSONObject(0);
            	String message = arg_object.getString("message");
            	
            	// Get Codova Activity
            	Activity activity = this.cordova.getActivity();
            	
            	// Show Alert Dialog
                new AlertDialog.Builder(activity)
	                .setTitle("My Alert")
	                .setMessage(message)
	                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // do something after confirmation
	                    }
	                 })
	                .setIcon(android.R.drawable.ic_dialog_alert)
	                .show();
            	
               callbackContext.success();
               return true;
            }
            
            // Case: other, non-supported action
            callbackContext.error("Invalid action");
            return false;
            
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}