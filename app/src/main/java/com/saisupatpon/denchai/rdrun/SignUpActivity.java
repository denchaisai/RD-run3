package com.saisupatpon.denchai.rdrun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {


    //Explicit การประกาศตัวแปร
    private EditText nameEditText,surnameEditText,userEditText,passwordEditText;
    //ตรง ชื่อตัวแปร กด ctrl spacebar


    private RadioGroup radioGroup;

    private RadioButton avata1RadioButton,avata2RadioButton,avata3RadioButton,avata4RadioButton, avata5RadioButton;
    //รับค่าจาก edittext
    private String nameString,surnameString,userString,passwordString,avataString;

    private static final String urlPHP="http://www.swiftcodingthai.com/rd/add_user_master.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // bind or initial widget คือการผูกความสัมพันธ์ระหว่างตัวแปร และ widget
        nameEditText = (EditText) findViewById(R.id.editText); //กด alt  enter
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvata);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);

        //Radio Controller ทำให้รับรู้ว่ามีการคลิก radio ตรงไหน
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            //ค่า checkedid จะเปลี่ยนทุกครั้งที่มีการคลิกเลือกหรือเปลี่ยน radio
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //โยนเลขไป ว่าแต่ละ radio เลขอะไร
                    case R.id.radioButton:
                        avataString = "0";
                        break;
                    case R.id.radioButton2:
                        avataString = "1";
                        break;
                    case R.id.radioButton3:
                        avataString = "2";
                        break;
                    case R.id.radioButton4:
                        avataString = "3";
                        break;
                    case R.id.radioButton5:
                        avataString = "4";
                        break;


                }
            }
        });

    } // main method

        // ถ้ากดปุ่ม signup จะส่งค่าขึ้น server 9.40pm ฟัง
    public void clickSignUpSign(View view) {
        //get value from edit text เปลี่ยนค่าเป็น string ดึงค่าจากช่องไปเก้บที่ตัวแปร trim เพื่อเทียบได้
        nameString=nameEditText.getText().toString().trim();
        surnameString=surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString=passwordEditText.getText().toString().trim();

        // การตรวจสอบหาช่องว่าง กรณีไม่ยอมกรอกข้อมูล
        // จะสร้าง method ในการ ตรวจสอบช่องว่าง checkspace()
        if (checkSpace()) {
            MyAlert myAlet = new MyAlert();
            myAlet.myDialog(this,R.drawable.bird48,"คุณยังใส่ข้อความไม่ครบ",
                    "กรุณากรอกข้อมูลให้ครบถ้วน");

        } else if (checkChoose()) {
            //true
            confirmValue();
        } else {
            //flase
            MyAlert myAlert=new MyAlert();
            myAlert.myDialog(this,R.drawable.doremon48,"ยังไม่เลือก Avata",
                    "กรุณาเลือก Avata ก่อนครับ");


        }

    }//clicksign

    private void confirmValue() {

        //ประกาศค่าคงที่
        MyConstant myConstant = new MyConstant();
        int[] avatInts=myConstant.getAvataInts();


        //ใช้ this เพราะ เชื่อมต่อภายใน
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //ทำให้ปุ่ม ควบคุม 3 ปุ่มล่างใช้งานไม่ได้
        builder.setCancelable(false);

        builder.setIcon(avatInts[Integer.parseInt(avataString)]);
        builder.setTitle("โปรดตรวจสอบข้อมูล");
        //เว้นบรรทัดด้วย \n
        builder.setMessage("Name = "+nameString + "\n"+
        "Surname = "+surnameString + "\n"+
        "User = "+userString+"\n"+
        "Password = "+passwordString);

        //เพิ่มปุ่ม 2 ปุ่ม
        //ปุ่ม ok อยู่ด้านซ้าย ตามผู้ใช้ถนัด
        //เมื่อกด cancel จะเอาภาพ
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //สร้าง method โยนขึ้น server
                uploadValueToServer();
                dialog.dismiss();
            }
        });
        builder.show();



    } // confirmValue

    private void uploadValueToServer() {
        //okhttp ต้องเพิ่ม library เพราะมีคนต่างหาก
        //ทำหน้าที่โยน string 5 ตัวขึ้นไปที่ server
        OkHttpClient okHttpClient = new OkHttpClient();
        //Fo กด ctrl space 2 ทีถึงจะมา
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name",nameString)
                .add("Surname",surnameString)
                .add("User",userString)
                .add("Password",passwordString)
                .add("Avata",avataString)
                .build();

        //จ่าหน้าซอง
        Request.Builder builder=new Request.Builder();
        Request request=builder.url(urlPHP).post(requestBody).build();

        //ส่งข้อมูลไป
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //กลับไปที่หน้าแรก
                //ใส่ log
                Log.d("31AugV1", "Result ==>" + response.body().string());
                finish();
            }
        });
    } // upload

    private boolean checkChoose() {
        boolean result = false;
        if (avata1RadioButton.isChecked()||
                avata2RadioButton.isChecked()||
                avata3RadioButton.isChecked()||
                avata4RadioButton.isChecked()||
                avata5RadioButton.isChecked()) {
            result=true;

        }

        return  result;
    }

    private boolean checkSpace() {

        boolean result=false;
        //ถ้าไม่กรอกข้อมูล
        if (nameString.equals("")||
                surnameString.equals("")||
                userString.equals("")||
                passwordString.equals("")) {
            result= true;

        }

        return result;

    }

}  // Main Class
