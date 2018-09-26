package com.emdad.mankenaretam;

/**
 * Created by HP on 2018/02/02.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emdad.mankenaretam.app.AppStatus;
import com.emdad.mankenaretam.model.User;
import com.emdad.mankenaretam.services.LoginService;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class MaterialDesignLogInForm extends AppCompatActivity {
    Context context;
    Activity ctx2;
    TextInputLayout name_layout,national_layout,mobile_layout,famil_layout;
    EditText nationalcode,name,famil,mobile;
    Button buttonlogin,btn1,btn2;
    LoginService loginService;
    MaterialDesignLogInForm ctx;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_login_form);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
//        int sdkVersion = Build.VERSION.SDK_INT;
//
//        if(sdkVersion>=23)
//
//        {
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
////            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//
//
//        }
        loginService = new LoginService(this);
        btn1 = (Button) findViewById(R.id.Loginr);
        btn2 = (Button) findViewById(R.id.Loginv);
        btn1.setEnabled(false);

        buttonlogin = (Button) findViewById(R.id.Login);
        nationalcode = (EditText) findViewById(R.id.national);
        name = (EditText) findViewById(R.id.name);
        famil = (EditText) findViewById(R.id.famil);
        mobile = (EditText) findViewById(R.id.mobile);

























        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finishAffinity();

                Intent myIntent = new Intent(MaterialDesignLogInForm.this,
                        Login.class);
                startActivity(myIntent);



            }
        });






        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



















                String s4 = nationalcode.getText().toString();
                String s2 = famil.getText().toString();
                String s3 = mobile.getText().toString();
                String s1 = name.getText().toString();


                String ed1=mobile.getText().toString();
                int size=ed1.length();
                String ed2=nationalcode.getText().toString();
                int size2=ed2.length();



                if(TextUtils.isEmpty(s1))
                {


name.setError("نام را وارد کنید");
return;

                }
                if(TextUtils.isEmpty(s2))
                {
                    name.setError(null);

                    famil.setError("نام خانوادگی را وارد کنید");
                    return;


                }
                if(TextUtils.isEmpty(s3)||(size<10))
                {




                    famil.setError(null);

                    mobile.setError("شماره موبایل را وارد کنید");
                    return;




                }




                if(TextUtils.isEmpty(s4)||(size2<10))
                {

                    mobile.setError(null);

                    nationalcode.setError("کد ملی را وارد کنید");
                    return;


                }


                if (!(AppStatus.getInstance(getApplicationContext()).isOnline())  )              {

//                    Toast.makeText(getApplicationContext(), "اینترنت همراه خاموش می باشد", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "اینترنت همراه خاموش می باشد", Snackbar.LENGTH_LONG);

// Changing message text color
                    snackbar.setActionTextColor(Color.RED);

// Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();

                }








               else
                {
                    nationalcode.setError(null);

                    User user = new User();
                    user.setName(name.getText().toString().trim());
                    user.setFamily(famil.getText().toString().trim());
                    user.setMobileNumber(Long.parseLong(mobile.getText().toString().trim()));

                    user.setNationalCode(nationalcode.getText().toString().trim());


                    try {

                        loginService.sendToServer(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }




                /*startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();*/

            }
        });





    }
}