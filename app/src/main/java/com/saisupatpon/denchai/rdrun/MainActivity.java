package com.saisupatpon.denchai.rdrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class  MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    } //Main Method นี่คือเมทธอด

    //Get Event from Click Button
    public void clickSignUpMain(View view) {
        // เคลื่อนย้ายการทำงาน จาก MainActivity.this ไป SignUpActivity.class
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }

} // Main Class นี่คือ คลาสหลัก
