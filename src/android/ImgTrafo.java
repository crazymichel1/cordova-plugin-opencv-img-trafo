package de.michaelskoehler.imgtrafo;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.content.Context;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.Activity;
//import android.R;


public class ImgTrafo extends CordovaPlugin {
    public static final String ACTION_SHOW_ALERT_DIALOG = "showAlertDialog";
    
    // opencv
    private static final String TAG = "OCVSample::Activity";
	//private CameraBridgeViewBase mOpenCvCameraView;
	
	Activity activity = this.cordova.getActivity();
	
	/*
    // prepare callback function for opencv loader (called later)
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(activity) {
    	@Override
    	public void onManagerConnected(int status) {
    		switch (status) {
	    		case LoaderCallbackInterface.SUCCESS:
	    		{
	    			Log.i(TAG, "OpenCV loading successful :)");
	    			mOpenCvCameraView.enableView();
	    		} break;
	    		default:
	    		{
	    			super.onManagerConnected(status);
	    		} break;
    		}
    	}
    };
    
    
    
    public void onCameraViewStarted(int width, int height) {
    	
    }
    
    public void onCameraViewStopped() {
    	
    }
    
    // ?
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	return inputFrame.rgba();
    }
    */
    
    // Cordova Plugin
	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
        	
        	// Case: showAlertDialog action
            if (ACTION_SHOW_ALERT_DIALOG.equals(action)) { 
            	
            	// Fetch arguments
            	// JSONObject arg_object = args.getJSONObject(0);
            	// String message = arg_object.getString("message");
            	
            	// get some application variables
            	Activity activity = this.cordova.getActivity();
            	/*
            	Context context = activity.getApplicationContext();
            	Resources resources = context.getResources();
            	String packageName = context.getPackageName();
            	*/
            	
            	// Show Alert Dialog
            	new AlertDialog.Builder(activity)
	                .setTitle("My Alert")
	                .setMessage("test")
	                .show();
            	
            	/*
            	LayoutInflater inflater = LayoutInflater.from(context);
            	View appearance = inflater.inflate(resources.getIdentifier("activity_main", "layout", packageName),null);
            	            	            	
            	// opencv
            	mOpenCvCameraView = (CameraBridgeViewBase)appearance.findViewById(resources.getIdentifier("HelloOpenCvView","id",packageName)); // instead of findViewById(R.id.HelloOpenCvView)
                mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
                mOpenCvCameraView.setCvCameraViewListener(this);
                
            	

            	OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, activity, mLoaderCallback);
                */
            	/*
            	// Get Codova Activity
            	Activity activity = this.cordova.getActivity();
            	*/
            	

	             
            	
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