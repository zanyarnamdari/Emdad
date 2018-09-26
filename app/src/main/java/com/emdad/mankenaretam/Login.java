package com.emdad.mankenaretam;

/**
 * Created by HP on 2018/02/02.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emdad.mankenaretam.services.LoginService3;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {
    Context context;
    TextInputLayout name_layout,national_layout,mobile_layout,famil_layout;
    EditText nationalcode,name,famil,mobile;
    Button buttonlogin,btn1,btn2;
    LoginService3 loginService;
    MaterialDesignLogInForm ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_login_form1);
       loginService= new LoginService3(this);
        btn1 = (Button) findViewById(R.id.Loginr1);
        btn2 = (Button) findViewById(R.id.Loginv1);
        btn2.setEnabled(false);

        buttonlogin = (Button) findViewById(R.id.Login1);
        nationalcode = (EditText) findViewById(R.id.national1);

        mobile = (EditText) findViewById(R.id.mobile1);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();

                Intent myIntent = new Intent(Login.this,
                        MaterialDesignLogInForm.class);
                startActivity(myIntent);



            }
        });






        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String s4 = nationalcode.getText().toString();
                String s3 = mobile.getText().toString();






                if(TextUtils.isEmpty(s3))
                {


                    mobile.setError("شماره موبایل را وارد کنید");
                    return;


                }
                if(TextUtils.isEmpty(s4))
                {

                    mobile.setError(null);

                    nationalcode.setError("کد ملی را وارد کنید");
                    return;


                }











                else
                {






                    try {

                        nationalcode.setError(null);

                        Long a= Long.valueOf(mobile.getText().toString().trim());
                        String b= nationalcode.getText().toString().trim();

                        loginService.send1(a,b);                    } catch (JSONException e) {
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