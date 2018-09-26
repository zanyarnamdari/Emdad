package com.emdad.mankenaretam.services;

import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.emdad.mankenaretam.Login;
import com.emdad.mankenaretam.MapsActivity;
import com.emdad.mankenaretam.app.AppController;
import com.emdad.mankenaretam.model.User;
import com.emdad.mankenaretam.utils.SharedpreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

//import com.myapplication.MaterialDesignLogInForm;


public class LoginService3 extends AppCompatActivity {

    static Login ctx;

    public LoginService3(Login ctx) {
        this.ctx = ctx;
    }

    public   void send1(Long a,String b) throws JSONException, UnsupportedEncodingException {

        String urlLogin = Services.LOGIN2;
        int reqType = Request.Method.POST;




        JSONObject jsonObject_one = new JSONObject();

        try {


            jsonObject_one.put("mobileNumber", a);
            jsonObject_one.put("nationalCode", b);



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

        Boolean res2 = Boolean.valueOf(response.getString("result"));

        if (res2 ){



            String   m = (response.getString("token"));

            try {

                decode(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ctx.finishAffinity();

            Intent intent = new Intent(ctx, MapsActivity.class);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            ctx.startActivity(intent);


        }


        if (!res2 ) {

            Toast.makeText(ctx, "اطلاعات اشتباه وارد شده است ", Toast.LENGTH_SHORT).show();

        }

    }

    private void decode (String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
//            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException, JSONException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);

        String a=new String(decodedBytes);



        SharedpreferenceHelper session = new SharedpreferenceHelper(ctx);
//
        User user=new User();
        JSONObject mainObject = new JSONObject(a);
        String  uniName = mainObject.getString("name");
        String  famil = mainObject.getString("family");
        String  id = mainObject.getString("userId");
        String  phone = mainObject.getString("phone");
        String  national = mainObject.getString("nationalCode");

        user.setName(uniName);
        user.setFamily(famil);
        user.setNationalCode(national);
        user.setMobileUserId(Long.parseLong(id));
        user.setMobileNumber(Long.parseLong(phone));



        session.createLoginSession(user);














        return new String(decodedBytes, "UTF-8");
    }


}


