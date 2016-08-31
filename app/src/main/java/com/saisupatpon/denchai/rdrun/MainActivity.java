package com.saisupatpon.denchai.rdrun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private ImageView imageView;
    private EditText userEditText, passwoEditText;

    private String userString, passwordString;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText5);
        passwoEditText = (EditText) findViewById(R.id.editText6);

        //load image from server
        Picasso.with(this).load("http://www.swiftcodingthai.com/rd/Image/rd_logo.png")
                .resize(150, 150).into(imageView);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } //Main Method นี่คือเมทธอด

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.saisupatpon.denchai.rdrun/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.saisupatpon.denchai.rdrun/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    //create inner class
    //สร้าง inner class ซ่อนอยู่ข้างในในการต่อเน็ต คือการทำงานแบบเทรด ต่อเน็ตถ้าต่อได้ต่อ ถ้าต่อไม่ได้ต่อใหม่ sycronise user asynctask ถ้าทำไม่สำเร็จแล้วจะทำงานใหม่
    private class SynUser extends AsyncTask<Void, Void, String> {


        //explicit
        private Context context;//สร้างท่อ
        private String myUserString,myPasswordString,truePasswordString
                ,nameString,surnameString,idString; //รับค่าจากตัวแปรที่ส่งเข้ามาจาก class ภายนอก

        private static final String urlJSON="http://www.swiftcodingthai.com/rd/get_user_master.php";
        //ตรวจสอบว่ามี user อยู่หรือไม่
        private  boolean statABoolean=true;

        public SynUser(Context context, String myUserString, String myPasswordString) {
            this.context = context;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
        }

        @Override
        protected String doInBackground(Void... params) {

            try { // เสี่ยงต่อการ error เอา error เก็บไว้ในตัวแปร e
                //สร้าง instant มาทำงาน
                OkHttpClient okHttpClient=new OkHttpClient();
                Request.Builder builder=new Request.Builder();
                Request request=builder.url(urlJSON).build();
                //เอามาแสดง
                Response response=okHttpClient.newCall(request).execute();
                //เมื่อไหร่เชื่อมต่อได้เสร็จจะรีเทิร์นบอดี สตริงทั้งหมดออกไป s
                return response.body().string();
            } catch (Exception e) {

                Log.d("31AugV2","e doInBack ==>"+e.toString());
                return null;
            }


        } //doinback พยายามต่อเน็ตเบื้องหลัง

        @Override  //หลังทำงานเสร็จ
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("31AugV2", "JSoN==>" + s);

            //กัน string error
            try {
                JSONArray jsonArray=new JSONArray(s); //ตัด string
                for (int i=0;i<jsonArray.length();i+=1) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                    if (myUserString.equals(jsonObject.getString("User"))) {
                        statABoolean=false;
                        truePasswordString = jsonObject.getString("Password");
                        nameString=jsonObject.getString("Name");
                        surnameString=jsonObject.getString("Surname");
                        idString=jsonObject.getString("id");





                    }//if
                }//for
                if (statABoolean) {
                    MyAlert myAlert=new MyAlert();
                    myAlert.myDialog(context,R.drawable.kon48,"User False","ไม่มี"+myUserString+"ในฐานข้อมูลของเรา");

                } else if (myPasswordString.equals(truePasswordString)) {
                    //4วิ แล้วหายไป คือ toast
                    Toast.makeText(context, "Welcome"+nameString+" "+surnameString+" "+idString,
                            Toast.LENGTH_SHORT).show();


                } else {
                    MyAlert myAlert=new MyAlert();
                    myAlert.myDialog(context,R.drawable.bird48,"Password False","Please Try again Password False");

                }


            } catch (Exception e) {
                Log.d("31AugV3","e onPost ==>"+e.toString());
            }

        }
    }//SynUser class

    public void clickSignInMain(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwoEditText.getText().toString().trim();
        //check space
        if (userString.equals("") || passwordString.equals("")) {
            //ยังไม่ใส่ข้อมูล
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, R.drawable.doremon48, "ข้อมูลไม่ครบถ้วน", "กรุณาใส่ให้ครบถ้วน");

        } else {
            //ใส่ข้อมูลครบทั้ง 2 ช่อง
            SynUser synUser = new SynUser(this, userString, passwordString);//เติม แconstruct
            synUser.execute();//ให้ประมวลผล
        }


    }  //clickSignIn


    //Get Event from Click Button
    public void clickSignUpMain(View view) {
        // เคลื่อนย้ายการทำงาน จาก MainActivity.this ไป SignUpActivity.class
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

} // Main Class นี่คือ คลาสหลัก
