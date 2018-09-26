package com.emdad.mankenaretam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emdad.mankenaretam.services.LoginServicep;
import com.emdad.mankenaretam.utils.SharedpreferenceHelper;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by HP on 2018/02/14.
 */

public class Peigiri extends AppCompatActivity {
    EditText security;
    Button btnpeigiri,btneshterak,btnertebat;
    Register ctx;
    LoginServicep loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peigiri);
        btnpeigiri = (Button) findViewById(R.id.sharc);
        btnertebat = (Button) findViewById(R.id.peigiric);
        btnertebat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                Intent myIntent = new Intent(Peigiri.this,
                        About.class);
                startActivity(myIntent);



            }



        });
        btneshterak = (Button) findViewById(R.id.etelaatc);
        btneshterak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareIt();
            }

            private void shareIt() {
//sharing implementation here
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "من کنارتم");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "برای دانلود برنامه روی لینک زیر کلیک کنید https://androidsolved.wordpress.com ");
                startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری با..."));
            }

        });

        security = (EditText) findViewById(R.id.peigiricodc);
        security.setEnabled(false);
        SharedpreferenceHelper sharedpreferenceHelper=new SharedpreferenceHelper(getApplicationContext());
        final String coding=sharedpreferenceHelper.getCod();
        security.setText(coding);



        loginService = new LoginServicep(this);


        btnpeigiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    security.setError(null);

                    loginService.send1(coding);



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });



    }
}
