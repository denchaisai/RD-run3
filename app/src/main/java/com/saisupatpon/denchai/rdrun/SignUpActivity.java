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
        avata1RadioButton= (RadioButton) findViewById(R.id.radioButton);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);



        // ถ้ากดปุ่ม signup จะส่งค่าขึ้น server 9.40pm ฟัง
    public void clickSignUpSign(View view) {

    }

}  // Main Class
