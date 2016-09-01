package com.saisupatpon.denchai.rdrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
        //post delay
        Handler handler = new Handler(); //จับเวลา
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        },1000);

    }//myloop

} // Main Class
