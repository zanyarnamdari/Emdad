package com.emdad.mankenaretam;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emdad.mankenaretam.app.AppStatus;
import com.emdad.mankenaretam.utils.AsyncCopy;
import com.emdad.mankenaretam.utils.SharedpreferenceHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HP on 2018/01/22.
 */

public class SplashActivity extends AppCompatActivity {
    private TextView txt;
    private ImageView img1, img2;

    // this where your file will be stored in sdcard in this case in folder (YOUR_FILE)
    static String BASE_FILE = Environment.getExternalStorageDirectory()+"/osmdroid/";
    ArrayList<String> TmpList = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);




        int sdkVersion = Build.VERSION.SDK_INT;

        if(sdkVersion>=23)

        {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


        }
        img1 = (ImageView) findViewById(R.id.img1);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        img1.clearAnimation();
        img1.startAnimation(anim);

        ArrayList<String> MyFiles = new ArrayList<String>();
        MyFiles.add("tiles.zip");

        // add your files here

        // create base folder if not exists
        File f = new File(BASE_FILE);
        if(!f.exists())
            f.mkdir();
        // this loop to check if files already coped or not  or any file delete
        for(int i=0;i<MyFiles.size();i++){
            File check = new File(BASE_FILE,MyFiles.get(i));
            if(!check.exists())
                TmpList.add(MyFiles.get(i)); // copy not coped items to other list
        }
        // now check if not all files copy or something remove
        if(TmpList.size()>0)
            new AsyncCopy(this, BASE_FILE, TmpList).execute("");
        else {
            Log.e("z", "copy: " );        }
        startTimer();
    }

    private void startTimer() {

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    /**
                     * Internet is available, Toast It!
                     */
                    checkSharedPerf();

//                    Toast.makeText(getApplicationContext(), "WiFi/Mobile Networks Connected!", Toast.LENGTH_SHORT).show();
                } else {
                    /**
                     * Internet is NOT available, Toast It!
                     */
                    checkSharedPerf();

                    Toast.makeText(getApplicationContext(), "اینترنت گوشی همراه خاموش می باشد", Toast.LENGTH_LONG).show();


                }




            }
        }.start();
    }


    private void checkSharedPerf() {
        SharedpreferenceHelper sharedpreferenceHelper = new SharedpreferenceHelper(getApplicationContext());


        if (sharedpreferenceHelper.isLoggedIn())


            //
            if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                /**
                 * Internet is available, Toast It!
                 */
                {startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                    finish();
                }
//                    Toast.makeText(getApplicationContext(), "WiFi/Mobile Networks Connected!", Toast.LENGTH_SHORT).show();
            } else {
                /**
                 * Internet is NOT available, Toast It!
                 */
                {startActivity(new Intent(getApplicationContext(),MainActivity_off.class));
                    finish();
                }


            }


            //










        else {
            startActivity(new Intent(getApplicationContext(),MaterialDesignLogInForm.class));
            finish();
        }
    }

}
