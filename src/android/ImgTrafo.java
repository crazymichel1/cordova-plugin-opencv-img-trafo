package de.michaelskoehler.imgtrafo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.res.Resources;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;
import android.view.LayoutInflater;
import android.view.View; 
import android.widget.ImageView;

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
            	
            	// start another activity
            	Context context = this.cordova.getActivity();
            	Intent intent = new Intent(context, OpenCvActivity.class);
            	intent.putExtra("de-michaelskoehler-imgtrafo-test", "blubb");
            	startActivity(intent);
            	
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