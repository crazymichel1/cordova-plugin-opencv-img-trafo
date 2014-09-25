package de.michaelskoehler.imgtrafo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.LayoutInflater;
import android.view.View; 
import android.widget.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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


public class OpenCVActivity extends Activity {
	private Activity activity;
	private Context context;
	private Resources resources;
	private String packageName;
	private int R_drawable_left07;
	private int R_drawable_left08;
	private int R_id_imageView1;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // define some application variables
    	activity = this;
    	context = activity.getApplicationContext();
    	resources = context.getResources();
    	packageName = context.getPackageName();
    	
    	// dynamically load resources
    	R_drawable_left07 = resources.getIdentifier("left07", "drawable", packageName);
    	R_drawable_left08 = resources.getIdentifier("left08", "drawable", packageName);
    	R_id_imageView1 = resources.getIdentifier("imageView1", "id", packageName);
    	
    	// dynamical version of setContentView(R.layout.activity_main);
    	//LayoutInflater inflater = LayoutInflater.from(context);
    	//View appearance = inflater.inflate(resources.getIdentifier("activity_main", "layout", packageName),null);
    	
    	this.setContentView(resources.getIdentifier("activity_main", "layout", packageName));
    	
        // init opencv and start actions (see mLoaderCallback below)
    	OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);

    	/*
    	String msg = getIntent().getStringExtra("de-michaelskoehler-imgtrafo-test");
        
        // my alert hello world
        new AlertDialog.Builder(this).setTitle("Delete entry").setMessage(msg).show();
        */
    }
        
    // callback function when OpenCVLoader.initAsync is finished
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
    	@Override
    	public void onManagerConnected(int status) {
    		switch (status) {
	    		case LoaderCallbackInterface.SUCCESS:
	    		{
	    			/*
	    			// OPENCV ACTIONS here:
	    			// setting image resource from drawable via bitmap
	    			Bitmap b_input = BitmapFactory.decodeResource(resources, R_drawable_left07);
	    	 		Bitmap b_output = BitmapFactory.decodeResource(resources, R_drawable_left08);
	    	 		
	    	 		//b_output = imgtrafo(b_input, b_output, 216, 70, 421, 108, 305, 447, 120, 354);
	    	 		b_output = canny(b_input);
	    	 		
	    	 		saveImageToInternalStorage(b_output, "bild2.png"); */
	    	 		
	    	 		Bitmap b_read = readImageFromInternalStorage("bild2.png");
	    	 		
	    	        ImageView imageView = (ImageView) findViewById(R_id_imageView1);
	    	 		imageView.setImageBitmap(b_read);
	    	 		
	    		} break;
	    		default:
	    		{
	    			super.onManagerConnected(status);
	    		} break;
    		}
    	}

    };
    
private static Bitmap canny(Bitmap image) {
    	
    	// convert image to matrix
    	Mat Mat1 = new Mat(image.getWidth(), image.getHeight(), CvType.CV_32FC1);
    	Utils.bitmapToMat(image, Mat1);
    	
    	// create temporary matrix2
    	Mat Mat2 = new Mat(image.getWidth(), image.getHeight(), CvType.CV_32FC1);
    	
    	// convert image to grayscale
    	Imgproc.cvtColor(Mat1, Mat2, Imgproc.COLOR_BGR2GRAY);
    	
    	// doing a gaussian blur prevents getting a lot of false hits
    	Imgproc.GaussianBlur(Mat2, Mat1, new Size(3, 3), 2, 2); //?
    	
    	// now apply canny function
    	int param_threshold1 = 25; // manually defined
    	int param_threshold2 = param_threshold1*3; //Cannys recommendation
    	Imgproc.Canny(Mat1, Mat2, param_threshold1, param_threshold2);
    	
    	// ?
        Imgproc.cvtColor(Mat2, Mat1, Imgproc.COLOR_GRAY2BGRA, 4);

    	// convert matrix to output bitmap
        Bitmap output = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(Mat1, output);
        return output;
    }
    
    
    private static Bitmap imgtrafo(Bitmap image1, Bitmap image2, int p1_x, int p1_y, int p2_x, int p2_y, int p3_x, int p3_y, int p4_x, int p4_y) {
    	// set output size same size as input
    	int resultWidth = image1.getWidth();
        int resultHeight = image1.getHeight();
    	
    	Mat inputMat = new Mat(image1.getWidth(), image1.getHeight(), CvType.CV_32FC1);
        Utils.bitmapToMat(image1, inputMat);
        Mat outputMat = new Mat(resultWidth, resultHeight, CvType.CV_32FC1); 

        Point ocvPIn1 = new Point(p1_x, p1_y);
        Point ocvPIn2 = new Point(p2_x, p2_y);
        Point ocvPIn3 = new Point(p3_x, p3_y);
        Point ocvPIn4 = new Point(p4_x, p4_y);
        List<Point> source = new ArrayList<Point>();
        source.add(ocvPIn1);
        source.add(ocvPIn2);
        source.add(ocvPIn3);
        source.add(ocvPIn4);
        Mat inputQuad = Converters.vector_Point2f_to_Mat(source);

        Point ocvPOut1 = new Point(256, 40); // manually set
        Point ocvPOut2 = new Point(522, 62);
        Point ocvPOut3 = new Point(455, 479);
        Point ocvPOut4 = new Point(134, 404);
        List<Point> dest = new ArrayList<Point>();
        dest.add(ocvPOut1);
        dest.add(ocvPOut2);
        dest.add(ocvPOut3);
        dest.add(ocvPOut4);
        Mat outputQuad = Converters.vector_Point2f_to_Mat(dest);      

        Mat perspectiveTransform = Imgproc.getPerspectiveTransform(inputQuad, outputQuad);

        Imgproc.warpPerspective(inputMat, 
                                outputMat,
                                perspectiveTransform,
                                new Size(resultWidth, resultHeight)); //?

        Bitmap output = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.RGB_565);
        Utils.matToBitmap(outputMat, output);
        return output;
    }
    
    private void saveImageToInternalStorage(Bitmap image, String filename_inclpng) {
    	try {
	    	// Use the compress method on the Bitmap object to write image to
	    	// the OutputStream
	    	FileOutputStream fos = context.openFileOutput(filename_inclpng, Context.MODE_PRIVATE);
	
	    	// Writing the bitmap to the output stream
	    	image.compress(Bitmap.CompressFormat.PNG, 100, fos);
	    	fos.close();
    	} catch (Exception e) {
	    	Log.e("err in saveToInternalStorage()", e.getMessage());
    	}
    }
	
    private Bitmap readImageFromInternalStorage(String filename) {
		try {
			File filePath = context.getFileStreamPath(filename);
			FileInputStream fi = new FileInputStream(filePath);
			return BitmapFactory.decodeStream(fi);
		} catch (Exception ex) {
			Log.e("err in readImageFromInternalStorage()", ex.getMessage());
			return null;
		}
	}


}
