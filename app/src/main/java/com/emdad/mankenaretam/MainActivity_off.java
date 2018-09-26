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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.emdad.mankenaretam.model.globaluser;
import com.emdad.mankenaretam.utils.SharedpreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;


/**
 * Created by HP on 2018/02/05.
 */

public class MainActivity_off extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 4;
    private MapView mapView;

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private RadioGroup radioGroup;

    public static final int RequestPermissionCode = 1;
    Button buttonEnable, buttonGet, aks, sharh, peigiri, etelaat, Submit2, ersal;

    double textViewLongitude;
    double textViewLatitude;
    private Activity activity;
    TextView textview;
    Bitmap bitmap;
    ImageView imag1, imag2, imag3, imag4, imag5;
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
    MainActivity_off ctx;

    int m = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        int sdkVersion = Build.VERSION.SDK_INT;

        if (sdkVersion >= 23)

        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//runtimepermission

//

//      ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);


//
//        if(sdkVersion<23)
//        {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            m = 0;
        }

//        }

//
//        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
//
//        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//            buildAlertMessageNoGps();
//        }
//


        //
        sharh = (Button) findViewById(R.id.shar);
        peigiri = (Button) findViewById(R.id.peigiri);
        imag2 = (ImageView) findViewById(R.id.imageView3);
        imag3 = (ImageView) findViewById(R.id.imageView4);
        imag4 = (ImageView) findViewById(R.id.imageView5);
        imag5 = (ImageView) findViewById(R.id.imageView6);

        etelaat = (Button) findViewById(R.id.etelaat);
        ersal = (Button) findViewById(R.id.ersal);


        peigiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity_off.this,
                        Peigiri.class);
                startActivity(myIntent);


            }
        });


        sharh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder = new AlertDialog.Builder(MainActivity_off.this);

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

                        String description = EditTextValue;
                        globaluser.des = description;

                        Toast.makeText(MainActivity_off.this, "شرح حادثه ثبت شد", Toast.LENGTH_LONG).show();
if (m==1) {
    alertdialog.cancel();
    finishAffinity();

    Intent myIntent = new Intent(MainActivity_off.this,
            MainActivity_off.class);
    startActivity(myIntent);


}

                        alertdialog.cancel();




                    }
                });

                alertdialog.show();

            }







        });


        radioGroup = (RadioGroup) findViewById(R.id.radio2);
        radioGroup.clearCheck();
        ///////////
        if (globaluser.organizedid ==1)
            radioGroup.check(R.id.radioButton6);
        else   if (globaluser.organizedid==2)
            radioGroup.check(R.id.radioButton7);
        else  if (globaluser.organizedid==3)
            radioGroup.check(R.id.radioButton8);
        else     if (globaluser.organizedid==4)
            radioGroup.check(R.id.radioButton9);

        ////////////
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

//                    Toast.makeText(MainActivity_off.this, rb.getText(), Toast.LENGTH_LONG).show();
                    globaluser.bbb = Integer.parseInt(String.valueOf(rb.getText()));

                    String aaa = String.valueOf(rb.getText());
                    globaluser.organizedid = Integer.parseInt(aaa);





                }

            }
        });

        SharedpreferenceHelper sharedpreferenceHelper = new SharedpreferenceHelper(getApplicationContext());
        long userId = sharedpreferenceHelper.getStoredId();

        globaluser.userid = userId;
        ersal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (globaluser.des == null) {

                        Toast.makeText(MainActivity_off.this, "شرح اتفاق ثبت شود", Toast.LENGTH_LONG).show();


                    }

                    if (globaluser.organizedid == 0) {

                        Toast.makeText(MainActivity_off.this, "ارگان مورد نظر انتخاب شود ", Toast.LENGTH_LONG).show();


                    }
                    if (globaluser.lat == 0) {

                        Toast.makeText(MainActivity_off.this, "لطفا برای یافتن موقعیت جغرافیایی مقداری صبر کنید..", Toast.LENGTH_LONG).show();


                    }



                    if ((globaluser.organizedid != 0) && (globaluser.des != null)&&(globaluser.lat != 0))

                    {

                        String number = "10000000008387";


                        try {
                            JSONObject object = new JSONObject();
                            object.put("organizeId", globaluser.organizedid);
                            object.put("userId", globaluser.userid);
                            object.put("description", globaluser.des);
                            object.put("lon", globaluser.lon);
                            object.put("lat", globaluser.lat);
                            String stringValue = String.valueOf(object);

                            byte[] bytes = stringValue.getBytes("UTF-8");


                            //String message = Base64.encodeToString(bytes, Base64.DEFAULT);
                            String message = new String(bytes);
                            globaluser.message = message;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        SmsManager sendsms = SmsManager.getDefault();


                        ArrayList<String> msgArray = sendsms.divideMessage(globaluser.message);

                        sendsms.sendMultipartTextMessage(number, null,
                                msgArray, null, null);


                        Toast.makeText(getApplicationContext(),
                                "درخواست امداد با پیامک ارسال شد",
                                Toast.LENGTH_LONG).show();

                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "درخواست شما ارسال نشد مجددا تلاش فرمایید",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        });


        //

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        checkPermissionsState();


    }


    private void checkPermissionsState() {
        int internetPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        int networkStatePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE);

        int writeExternalStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int wifiStatePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE);

        if (internetPermissionCheck == PackageManager.PERMISSION_GRANTED &&
                networkStatePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                writeExternalStoragePermissionCheck == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermissionCheck == PackageManager.PERMISSION_GRANTED &&
                fineLocationPermissionCheck == PackageManager.PERMISSION_GRANTED &&
                wifiStatePermissionCheck == PackageManager.PERMISSION_GRANTED) {

            setupMap();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_WIFI_STATE},
                    MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean somePermissionWasDenied = false;
                    for (int result : grantResults) {
                        if (result == PackageManager.PERMISSION_DENIED) {
                            somePermissionWasDenied = true;
                        }
                    }
                    if (somePermissionWasDenied) {
                        Toast.makeText(this, "Cant load maps without all the permissions granted", Toast.LENGTH_SHORT).show();
                    } else {
                        setupMap();
                    }
                } else {
                    Toast.makeText(this, "Cant load maps without all the permissions granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private void setupMap() {

        mapView = (MapView) findViewById(R.id.mapview2);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        //setContentView(mapView); //displaying the MapView

        mapView.getController().setZoom(20); //set initial zoom-level, depends on your need
        //mapView.getController().setCenter(ONCATIVO);
        //mapView.setUseDataConnection(false); //keeps the mapView from loading online tiles using network connection.
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        MyLocationNewOverlay oMapLocationOverlay = new MyLocationNewOverlay(getApplicationContext(), mapView);
        mapView.getOverlays().add(oMapLocationOverlay);
        oMapLocationOverlay.enableFollowLocation();
        oMapLocationOverlay.enableMyLocation();
        oMapLocationOverlay.enableFollowLocation();

        CompassOverlay compassOverlay = new CompassOverlay(this, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        mapView.setMapListener(new DelayedMapListener(new MapListener() {
            public boolean onZoom(final ZoomEvent e) {
                MapView mapView = (MapView) findViewById(R.id.mapview2);

                String latitudeStr = "" + mapView.getMapCenter().getLatitude();
                String longitudeStr = "" + mapView.getMapCenter().getLongitude();

                String latitudeFormattedStr = latitudeStr.substring(0, Math.min(latitudeStr.length(), 7));
                String longitudeFormattedStr = longitudeStr.substring(0, Math.min(longitudeStr.length(), 7));

                Log.i("zoom", "" + mapView.getMapCenter().getLatitude() + ", " + mapView.getMapCenter().getLongitude());
//                TextView latLongTv = (TextView) findViewById(R.id.textView);
                String latLongTv = (latitudeFormattedStr + longitudeFormattedStr);


                return true;
            }

            public boolean onScroll(final ScrollEvent e) {
                MapView mapView = (MapView) findViewById(R.id.mapview2);

                String latitudeStr = "" + mapView.getMapCenter().getLatitude();
                String longitudeStr = "" + mapView.getMapCenter().getLongitude();

                String latitudeFormattedStr = latitudeStr.substring(0, Math.min(latitudeStr.length(), 7));
                String longitudeFormattedStr = longitudeStr.substring(0, Math.min(longitudeStr.length(), 7));

                Log.i("scroll", "" + mapView.getMapCenter().getLatitude() + ", " + mapView.getMapCenter().getLongitude());
//             S latLongTv = (TextView) findViewById(R.id.textView);
                String latLongTv = latitudeFormattedStr + longitudeFormattedStr;


                return true;
            }
        }, 1000));
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void setCenterInMyCurrentLocation() {
        if (mLastLocation != null) {
            mapView.getController().setCenter(new GeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude()));


        } else {
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;

        }
        if (m == 0) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation!=null)
            {
            globaluser.lat = mLastLocation.getLatitude();
            globaluser.lon = mLastLocation.getLongitude();}

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//
//            return true;
//        } else if (id == R.id.action_locate) {
//
//            setCenterInMyCurrentLocation();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


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


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("آیا به برنامه ی من کنارتم اجازه می دهید gps گوشی شما را فعال کند؟")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));


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
