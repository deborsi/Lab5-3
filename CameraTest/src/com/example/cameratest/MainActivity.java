package com.example.cameratest;
// This project is adopted from https://eclass.srv.ualberta.ca/mod/resource/view.php?id=1136415
import java.io.File;

import com.example.cameratest.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

    Uri imageFileUri;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v){
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    //This method creates an intent. 
    //It is told that we need camera action, and the results should be saved in a location that is sent to the intent.
    public void takeAPhoto() {
		
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCameraTest";
		File folderF = new File(folder);
		
		if (!folderF.exists()){
			folderF.mkdir();
		}
			
		String imageFilePath = folder + File.separator + 
				String.valueOf(System.currentTimeMillis()) + ".jpg" ;
		
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    
    //This method is run after returning back from camera activity:
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
    	TextView tv = (TextView) findViewById(R.id.status);
    	
    	if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){			
			if (resultCode == RESULT_OK){
				if(data != null)
				{
					tv.setText(data.getStringExtra("message"));
				}else{
					tv.setText("Photo OK!");
				}
				ImageButton button = (ImageButton)findViewById(R.id.TakeAPhoto);
				button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
			}
			else if (resultCode == RESULT_CANCELED){
				tv.setText("Photo was canceled!");
			}else{
				tv.setText("What happened?!!");
				}
    	}
    }
}