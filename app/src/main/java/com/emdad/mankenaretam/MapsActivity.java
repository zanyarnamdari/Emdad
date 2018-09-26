package com.emdad.mankenaretam;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.emdad.mankenaretam.model.globaluser;
import com.emdad.mankenaretam.services.Services;
import com.emdad.mankenaretam.utils.SharedpreferenceHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends
        FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    String image;

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private RadioGroup radioGroup;
    private Uri filePath;

    public static final int RequestPermissionCode = 1;
    Button buttonEnable, buttonGet, aks, sharh, peigiri, etelaat, Submit2, ersal;

    double textViewLongitude;
    double textViewLatitude;
    private Activity activity;
    TextView textview;
    Bitmap bitmap;

    Context context;
    Intent intent1;
    Location location;
    LocationManager locationManager;
    boolean GpsStatus = false;
    Criteria criteria;
    String Holder;
    AlertDialog.Builder builder;
    LayoutInflater layoutinflater;
    EditText edittext;
    AlertDialog alertdialog;
    String EditTextValue;
    Intent intent;
    boolean check = true;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView myImage;
    private TextView txtPath;
    private static final int IMAGE_REQUEST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int sdkVersion = Build.VERSION.SDK_INT;
//permision
//if(sdkVersion>=23)
//
//{
//
//    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
////         ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//
//}


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }


        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }


        myImage = (ImageView) findViewById(R.id.selectedImg);
        txtPath = (TextView) findViewById(R.id.txtPath);
        sharh = (Button) findViewById(R.id.shar);
        peigiri = (Button) findViewById(R.id.peigiri);

        etelaat = (Button) findViewById(R.id.etelaat);
        ersal = (Button) findViewById(R.id.ersal);
//1
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//


        sharh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(MapsActivity.this);

                layoutinflater = getLayoutInflater();

                View Dview = layoutinflater.inflate(R.layout.pop, null);

                builder.setCancelable(false);

                builder.setView(Dview);

                edittext = (EditText) Dview.findViewById(R.id.editText1);
                Submit2 = (Button) Dview.findViewById(R.id.button1);
                alertdialog = builder.create();

                Submit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        EditTextValue = edittext.getText().toString();

                        String description = EditTextValue;///////////////////////////////////////////////////++


                        globaluser.des = description;
                        Toast.makeText(MapsActivity.this, "شرح حادثه ثبت شد", Toast.LENGTH_LONG).show();

                        alertdialog.cancel();




//


                    }
                });

                alertdialog.show();
            }
        });


        radioGroup = (RadioGroup) findViewById(R.id.radio);
        radioGroup.clearCheck();
        ///////////


        ////////////
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
//                    Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_LONG).show();

                    String aaa = String.valueOf(rb.getText());
                    globaluser.organizedid = Integer.parseInt(aaa);
                }
            }
        });


        SharedpreferenceHelper sharedpreferenceHelper = new SharedpreferenceHelper(getApplicationContext());
        long userId = sharedpreferenceHelper.getStoredId();

        globaluser.userid = userId;


        peigiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MapsActivity.this,
                        Peigiri.class);
                startActivity(myIntent);

//zzz
            }
        });


        ersal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (globaluser.organizedid == 0) {

                    Toast.makeText(MapsActivity.this, "ارگان مورد نظر انتخاب شود ", Toast.LENGTH_LONG).show();


                }


                if ((globaluser.organizedid != 0) && (globaluser.des != null))


//                uploadMultipart(image);

                {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 100);

                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();

            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

int h = 0,w=0;
                int origWidth = bitmap.getWidth();
                int origHeight = bitmap.getHeight();
//                String m= String.valueOf(origWidth);
//                Log.e(m, "onActivityResult: ");



                if (origWidth>=origHeight)
                {


                    if (origWidth<=1000)
                    {


                        w=origWidth/2;
                        h=origHeight/2;

                    }

                    if (origWidth>1000 &origWidth<=2000)
                    {


                        w=origWidth/4;
                        h=origHeight/4;

                    }
                    if (origWidth>2000 &origWidth<=3000)
                    {


                        w=origWidth/6;
                        h=origHeight/6;

                    }
                    if (origWidth>3000 &origWidth<=4000)
                    {


                        w=origWidth/8;
                        h=origHeight/8;

                    }
                    if (origWidth>4000 &origWidth<=5000)
                    {


                        w=origWidth/10;
                        h=origHeight/10;

                    }

                    if (origWidth>5000)
                    {


                        w=origWidth/12;
                        h=origHeight/12;

                    }


//                    String m= String.valueOf(w);
//                    String n= String.valueOf(h);
//
//                    Log.e(m, n);

                }



                else if (origWidth<origHeight)
                {


                    if (origHeight<=1000)
                    {


                        w=origWidth/2;
                        h=origHeight/2;

                    }

                    if (origHeight>1000 &origHeight<=2000)
                    {


                        w=origWidth/4;
                        h=origHeight/4;

                    }
                    if (origHeight>2000 &origHeight<=3000)
                    {


                        w=origWidth/6;
                        h=origHeight/6;

                    }
                    if (origHeight>3000 &origHeight<=4000)
                    {


                        w=origWidth/8;
                        h=origHeight/8;

                    }
                    if (origHeight>4000 &origHeight<=5000)
                    {


                        w=origWidth/10;
                        h=origHeight/10;

                    }

                    if (origHeight>5000)
                    {


                        w=origWidth/12;
                        h=origHeight/12;

                    }

                }









//                String m= String.valueOf(w);
//                String n= String.valueOf(h);
//
//                Log.e(m, n);

               bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);


                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);












        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) throws UnsupportedEncodingException {

        final String org = String.valueOf(globaluser.organizedid);
        final String lon = String.valueOf(globaluser.lon);
        final String lat = String.valueOf(globaluser.lat);
        final String user = String.valueOf(globaluser.userid);


        String f = URLEncoder.encode(globaluser.des, "utf-8");
        final String des = f;


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Services.sendvps,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
//                            Toast.makeText(getApplicationContext(),"درخواست کمک با موفقیت فرستاده شد" , Toast.LENGTH_SHORT).show();

                            String cod = obj.getString("TrackingCode");
//                            String cod = obj.getString("recieptCode");


                            SharedpreferenceHelper se = new SharedpreferenceHelper(MapsActivity.this);
//


                            se.getttt(cod);


//                            SharedpreferenceHelper sharedpreferenceHelper=new SharedpreferenceHelper(getApplicationContext());
//                            String coding=sharedpreferenceHelper.getCod();


                            Toast.makeText(getApplicationContext(), "درخواست مورد نظر با موفقیت فرستاده شد", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "خطا در فرستادن اطلاعات دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("organizeld", org);
                params.put("userId", user);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("descriptions", des);



                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("files", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "برای خروج دو بار کلیک کنید",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    //2
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        globaluser.lat = location.getLatitude();
        globaluser.lon = location.getLongitude();


        MarkerOptions markerOptions = new MarkerOptions();
//        String a= String.valueOf(latLng);
//        Toast.makeText(MainActivity.this,a , Toast.LENGTH_LONG).show();

        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    //
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();


    }


}

