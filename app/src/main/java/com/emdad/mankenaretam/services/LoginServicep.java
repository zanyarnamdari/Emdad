package com.emdad.mankenaretam.services;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.emdad.mankenaretam.Peigiri;
import com.emdad.mankenaretam.app.AppController;
import com.emdad.mankenaretam.model.globaluser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

//import com.myapplication.MaterialDesignLogInForm;


public class LoginServicep {
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    Peigiri ctx;
    public void setBasIn(BaseAnimatorSet bas_in) {
        this.mBasIn = bas_in;
    }

    public void setBasOut(BaseAnimatorSet bas_out) {
        this.mBasOut = bas_out;
    }
    public LoginServicep(Peigiri ctx) {
        this.ctx = ctx;
    }

    public void send1(String b) throws JSONException, UnsupportedEncodingException {

        String url= Services.peigiri+b;
        JSONArray obj = null  ;


        JsonArrayRequest jsonObjReq=null;



        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        jsonObjReq = new JsonArrayRequest (Request.Method.GET,
                url,null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {






                        if (response!=null){


                            parseJson(response);
                        }

                        if(response==null){



                        }



                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(context, R.string.error_connect, Toast.LENGTH_SHORT).show();



                if ((error instanceof NetworkError) || (error instanceof NoConnectionError) ) {




                    Toast.makeText(ctx,"اینترنت همراه گوشی شما خاموش می باشد", Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();
                    return;
                }
                if (error instanceof TimeoutError){


                    Toast.makeText(ctx, "TimeOut", Toast.LENGTH_SHORT).show();
                    // context.getDialog().dismiss();
                    return;
                }


                if ((error instanceof ServerError) || (error instanceof AuthFailureError)){

                    //  Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                    // context.getDialog().dismiss();


                    Toast.makeText(ctx, "ServerError", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        }

        )

        {
            public String getBodyContentType()
            {
                return "application/json";
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Log.e("Body",new String(jsonObjReq.getBody(), "UTF-8"));
        String tag_json_arry = "json_array_req";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);

    }


    public void parseJson(JSONArray response){

        if( response!=null) {
            JSONArray inputArray = null;
            try {
                inputArray = new JSONArray(response.toString());
            } catch (JSONException e) {

                Toast.makeText(ctx, "zzz", Toast.LENGTH_SHORT).show();


                e.printStackTrace();
            }
            try {
                JSONObject jo = inputArray.getJSONObject(0);

                JSONObject object = jo.getJSONObject("helpResponseMessage");
                String attr1 = object.getString("descrptions");
globaluser.sec=1;



                MaterialDialogOneBtn(attr1);





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (globaluser.sec==0){
String f="درخواست شما در دست اقدام است";
            MaterialDialogOneBtn(f);


        }








        }

    private void MaterialDialogOneBtn(String m) {
        final MaterialDialog dialog = new MaterialDialog(ctx);
        dialog//
                .title("آخرین وضعیت درخواست شما :")
                .titleTextSize(18)
                .btnNum(1)
                .content(m)//
                .btnText("ok")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {

                dialog.dismiss();
            }
        });
    }


}
