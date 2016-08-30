package com.saisupatpon.denchai.rdrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {


    //Explicit การประกาศตัวแปร
    private EditText nameEditText,surnameEditText,userEditText,passwordEditText;
    //ตรง ชื่อตัวแปร กด ctrl spacebar


    private RadioGroup radioGroup;

    private RadioButton avata1RadioButton,avata2RadioButton,avata3RadioButton,avata4RadioButton, avata5RadioButton;
    //รับค่าจาก edittext
    private String nameString,surnameString,userString,passwordString,avataString;




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

        }

    }//clicksign

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
