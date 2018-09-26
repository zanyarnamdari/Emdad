package com.emdad.mankenaretam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emdad.mankenaretam.services.LoginService2;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


/**
 * Created by HP on 2018/02/05.
 */

public class Register extends AppCompatActivity  {
    EditText security;
    Button buttonlogin;
    Register ctx;
    LoginService2 loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        buttonlogin = (Button) findViewById(R.id.Login2);
        security = (EditText) findViewById(R.id.name2);

        loginService = new LoginService2(this);


        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String s1 = security.getText().toString();
                String ed2=security.getText().toString();
                int size2=ed2.length();




                if(TextUtils.isEmpty(s1)||(size2<4))
                {


                    security.setError("کد امنیتی  را وارد کنید");
                    return;

                }

               else{    try {


                    security.setError(null);

                    int a= Integer.parseInt(security.getText().toString().trim());

                    loginService.send(a);



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                }



            }
        });



    }
}
