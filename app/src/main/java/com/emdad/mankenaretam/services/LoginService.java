package com.emdad.mankenaretam.services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.emdad.mankenaretam.MaterialDesignLogInForm;
import com.emdad.mankenaretam.Register;
import com.emdad.mankenaretam.app.AppController;
import com.emdad.mankenaretam.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

//import com.myapplication.MaterialDesignLogInForm;

/**
 * Created by Shima on 11/26/2017.
 */


public class LoginService extends AppCompatActivity {
Context context;
    MaterialDesignLogInForm ctx;


    public LoginService(MaterialDesignLogInForm ctx) {
        this.ctx = ctx;
    }

    public   void sendToServer(User user) throws JSONException, UnsupportedEncodingException {
        Log.e("zzzzzz", "onClick:1 ");

        final String urlLogin = Services.LOGIN;
        final int reqType = Request.Method.POST;




        final JSONObject jsonObject_one = new JSONObject();

        try {


            jsonObject_one.put("name", user.getName());
            jsonObject_one.put("family", user.getFamily());
            jsonObject_one.put("mobileNumber", user.getMobileNumber());
            jsonObject_one.put("nationalCode", user.getNationalCode());



        }
        catch (JSONException e) {
            e.printStackTrace();

        }

        JsonObjectRequest jsonObjReq=null;

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        jsonObjReq = new JsonObjectRequest(reqType,
                urlLogin,jsonObject_one,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response!=null) {

                            try {
                                parseJson(response);

                            }
                            catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {




                if ((error instanceof NetworkError) || (error instanceof NoConnectionError) ) {

                    Toast.makeText(ctx,"اینترنت گوشی همراه خاموش می باشد", Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();
                    return;
                }

                if (error instanceof TimeoutError){

                    Toast.makeText(ctx, "TimeOut", Toast.LENGTH_SHORT).show();

                    return;
                }

                if ((error instanceof ServerError) || (error instanceof AuthFailureError)){

                    Toast.makeText(ctx, "خطا از سرور ", Toast.LENGTH_SHORT).show();

                    return;
                }

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };



        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Log.e("Body",new String(jsonObjReq.getBody(), "UTF-8"));
        String tag_json_arry = "json_array_req";
// Adding request to request queue
        // mRequestQueue.add(jsonObjReq);
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);}




    public void parseJson(JSONObject response) throws JSONException {

        boolean res = response.getBoolean("result");

        if (res) {

//            SharedpreferenceHelper session = new SharedpreferenceHelper(ctx);
//
 //           User user=new User();
//
    //       user.setMobileUserId(Long.parseLong(response.getString("id")));
//
//
//            session.createLoginSession(user);
//            ctx.finishAffinity();



            ///////




                Intent intent = new Intent(ctx, Register.class);


                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                ctx.startActivity(intent);



/////////////////






        }
        if (!res) {

            Toast.makeText(ctx, "کد ملی تکراری می باشد ", Toast.LENGTH_LONG).show();
        }

    }



}


