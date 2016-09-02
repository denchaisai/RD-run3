package com.saisupatpon.denchai.rdrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private GoogleMap mMap;
    private String idString, avataString,nameString,surnameString;
    private ImageView imageView;
    private TextView nameTextview, surnameTextview;
    private int[] avataInts;
    private double userLatADouble=13.809409, userLngADouble = 100.5134380;//connect
    //getlocation
    private LocationManager locationManager; //ค้นหาพิกัดที่อยู่บนโลก ไม่ใช่บนแผนที่นะ คนละตัวกัน
    private Criteria criteria; //ปันจักรยานขึ้นเนินจะรู้
    private static final String urlPHP = "http://swiftcodingthai.com/rd/edit_location_pop.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_service);

        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView7);
        nameTextview= (TextView) findViewById(R.id.textView7);
        surnameTextview = (TextView) findViewById(R.id.textView9);


        //Get value From Intent
        idString = getIntent().getStringExtra("id");
        avataString = getIntent().getStringExtra("Avata");
        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");

        //Setup Location เปิด location service
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        //รายละเอียการค้นหา
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);//ตัดค่าแกน z ออก ชัืน 10 กับบชั้น  1  ค่าเดียวกัน



        // show text
        nameTextview.setText(nameString);
        surnameTextview.setText(surnameString);

        //show avata
        MyConstant myConstant = new MyConstant();
        avataInts=myConstant.getAvataInts();
        imageView.setImageResource(avataInts[Integer.parseInt(avataString)]);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } // Main Method ทำหน้าที่หลักในการสั่ง

    //syncronice data
    private class SynAllUser extends AsyncTask<Void, Void, String> {

        //Explicit
        //ดึง method mmap เข้ามา
        private Context content; //สร้างท่อเชื่อมต่อ
        private GoogleMap googleMap;//ที่จะปัก maker เข้าไป
        private static final String urlJSON = "http://swiftcodingthai.com/rd/get_user_master.php";
        //ดึงชื่อมาแสดงบนแผนที่
        private  String[] nameStrings, surnameStrings; // ดึง array ออกมา
        //ดึง avata
        private int[] avataInts;

        //ดึง lat lng
        private double[] latDoubles, lngDoubles;

        public SynAllUser(Context content, GoogleMap googleMap) {
            this.content = content;
            this.googleMap = googleMap;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

                //เปลี่ยน string เป็น data


            } catch ( Exception e) {
                Log.d("2SepV2", "e.doIn==>" + e.toString());
                return null;
            }


        }

        //หลังจาก doinback ทำานเสร็จ
        //alt insert

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            Log.d("2SepV2", "JSON==>" + s);

            //ทำ for loop ดึง array ออกมาแสดง
            try {
                JSONArray jsonArray = new JSONArray(s);

                nameStrings = new String[jsonArray.length()];//จองหน่วยความจำเท่ากับ
                surnameStrings = new String[jsonArray.length()];
                avataInts = new int[jsonArray.length()];
                latDoubles = new double[jsonArray.length()];
                lngDoubles = new double[jsonArray.length()];

                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    surnameStrings[i] = jsonObject.getString("Surname");
                    avataInts[i] = Integer.parseInt(jsonObject.getString("Avata"));
                    latDoubles[i] = Double.parseDouble(jsonObject.getString("Lat"));
                    lngDoubles[i] = Double.parseDouble(jsonObject.getString("Lng"));

                    //รูปภาพ
                    MyConstant myConstant = new MyConstant();
                    int[] iconInts = myConstant.getAvataInts();


                    //create marker
                    googleMap.addMarker(new MarkerOptions()

                            .position(new LatLng(latDoubles[i], lngDoubles[i]))
                            .icon(BitmapDescriptorFactory.fromResource(iconInts[avataInts[i]]))
                            .title(nameStrings[i]+" "+surnameStrings[i])
                    );




                }


            } catch (Exception e) {
                Log.d("2SepV3", "e onPost ==>" + e.toString());
            }



        }



    }// synalluser class

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        //หยุดและรับค่ามาใหม่
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);//ค่าlocation ที่ได้จาการต่อ net ทัง wifi และอื่นๆ
        //กด ctrl p ถ้าไม่ขขึ้น hint
        if (networkLocation!=null) {//ถ้ามือถือต่อเน็ตอยู่
            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(locationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();

        }
        //ทำ loop วน

    } //onresume

    //อะไรคือ activity โหมด pasuse รอ
    //overlide ดึง
    //alt insert


    @Override
    protected void onStop() {
        super.onStop();//การปิด app
        //ถ้ามีการปิด app ให้หยุดโปรเซสด้วย
        locationManager.removeUpdates(locationListener);// ให้หยุดโปรเซสการค้นหา

    }

    // หาตำแหน่งปัจจุบันจาก sim หรือจาก ip
    public Location myFindLocation(String strProvider) {

        Location location=null;

        if (locationManager.isProviderEnabled(strProvider)) {
            //ถ้ามีค่า gps ทำตรงนี้
            //1000=1 วินาที ค้นทุกๆ 1 วินาที  ถ้าเปลี่ยนแปลงเกิน ๅ10 เมตรจะค้นหา
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);




        } else {
            Log.d("1SepV1", "Cannot find Location");
        }

        return location;

    }

    public LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            userLatADouble=location.getLatitude();
            userLngADouble = location.getLongitude();

        } // onLocationChange

        @Override //มีการเปลี่ยนแปลง ขยับไปมาให้ทำอะไร
        public void onStatusChanged(String s, int i, Bundle bundle) {

        } //

        @Override //ออกเน็ตได้ให้ทำอะไร
        public void onProviderEnabled(String s) {

        }

        @Override  // ถ้าออก net ไม่่ได้ ตัวนี้ทำงาน
        public void onProviderDisabled(String s) {

        }
    };




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //setup center of map
        LatLng latLng = new LatLng(userLatADouble, userLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16)); //zoom 16    1=ไกลสุด ติดพื้นคือ 20

        //loop
        myLoop(); // ทำไม่มีวันสิ้นสุด alt enter สร้าง mylooop ด้านล่าง


    } // onMap ทำหน้าที่แสดงแผนที่

    private void myLoop() {
        // to do
        Log.d("1SepV2", "Lat==>" + userLatADouble);
        Log.d("1SepV2", "Lng==>" + userLngADouble);

        //โยนค่า update lat lng บน mysql
        editLatLngOnServer();

        //อ่านพิกัดทุกคนออกมา ทำmarker
        //create marker
        createMarker();

        //post delay
        Handler handler = new Handler(); //จับเวลา
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        },1000);

    }//myloop

    private void createMarker() {

       // Clear marker เก่าออกให้หมดก่อน
        mMap.clear();

        //เอาค่า xy
        SynAllUser synAllUser = new SynAllUser(this,mMap);//ต่อท่อ ใช้ this
        synAllUser.execute();



    }//create marker

    private void editLatLngOnServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //สร้าง request body ทำเป็นก้อน
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", idString)
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();
        //จ่าหน้าซอง
        Request.Builder builder = new Request.Builder();
        //
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //ออกไม่ได้ เช่สมือถือไม่ต่อเน็ต server ตายไป
                Log.d("2SepV1", "e==>" + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //ต่อได้
                Log.d("2SepV1", "Resul==>" + response.body().string());

            }
        });



    }//editLatLng

} // Main Class
