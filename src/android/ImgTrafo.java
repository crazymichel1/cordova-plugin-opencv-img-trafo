package de.michaelskoehler.imgtrafo;

import java.io.*;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.SurfaceView;
import android.view.LayoutInflater;
import android.view.View; 

public class ImgTrafo extends CordovaPlugin {
    public static final String ACTION_SHOW_ALERT_DIALOG = "showAlertDialog"; //plugin
    private static String debugVars = ""; //debugging
    
    //plugin
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
        	
        	// Case: showAlertDialog action
            if (ACTION_SHOW_ALERT_DIALOG.equals(action)) { 
            	/*
            	// Fetch arguments from cordova js plugin
            	JSONObject arg_object = args.getJSONObject(0);
            	String message = arg_object.getString("message");
            	*/
            	
            	// get some application variables
            	Activity activity = this.cordova.getActivity();
            	Context context = activity.getApplicationContext();
            	Resources resources = context.getResources();
            	String packageName = context.getPackageName();
            	            	
            	LayoutInflater inflater = LayoutInflater.from(context);
            	View appearance = inflater.inflate(resources.getIdentifier("activity_main", "layout", packageName),null);
            	
                BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this.cordova.getActivity()) {
                	@Override
                	public void onManagerConnected(int status) {
                		switch (status) {
            	    		case LoaderCallbackInterface.SUCCESS:
            	    		{
            	            	debugVars = debugVars.concat("loading error");
            	                
            	                // my alert hello world
            	                new AlertDialog.Builder(activity).setTitle("Alert").setMessage("loading successful").show();
            	                
            	    		} break;
            	    		default:
            	    		{
            	    			super.onManagerConnected(status);
            	    		} break;
                		}
                	}
                };
            	
                // init opencv and start actions (see mLoaderCallback below)
            	OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, activity, mLoaderCallback);
            	
               callbackContext.success();
               return true;
            }
            
            // Case: other, non-supported action
            callbackContext.error("Invalid action");
            return false; 
            
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            
            // get stack trace as string
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            
            // build error msg
            String errorMsg = "Debug-Vars: \n";
            errorMsg = errorMsg.concat(this.debugVars);
            errorMsg = errorMsg.concat(" \n Stack Trace: ");
            errorMsg = errorMsg.concat(sw.toString());
            
            callbackContext.error(errorMsg);
            return false;
        } 
    }
}