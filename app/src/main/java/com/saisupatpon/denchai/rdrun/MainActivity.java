package com.saisupatpon.denchai.rdrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class  MainActivity extends AppCompatActivity {

    //Explicit
    private ImageView imageView;
    private EditText userEditText,passwoEditText;





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
        .resize(150,150).into(imageView);

    } //Main Method นี่คือเมทธอด

    public void clickSignInMain(View view) {

    }  //clickSignIn


    //Get Event from Click Button
    public void clickSignUpMain(View view) {
        // เคลื่อนย้ายการทำงาน จาก MainActivity.this ไป SignUpActivity.class
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }

} // Main Class นี่คือ คลาสหลัก
