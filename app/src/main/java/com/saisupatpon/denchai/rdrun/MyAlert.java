package com.saisupatpon.denchai.rdrun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by user on 8/30/2016.
 */

// สร้าง method ตรวจสอบหน้าจอที่ยังไม่กรอก
public class MyAlert {
    //สร้าง method เรียกใช้จาก class ภายนอกได้ด้วย

    public void myDialog(Context context,
                         int intIcon,
                         String strTitle,
                         String strMessage) {

        // ต้องสืบทอดคลาส
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // เอา instant builder มาทำงาน ไม่ให้ปุ่มทำงานถ้ายังใส่ไม่ครบ
        builder.setCancelable(false);
        //เอารูปไหนขึ้นมาแสดง
        builder.setIcon(intIcon);
        //ใส่หัวข้อไดตเติ้ลที่จะแจ้งให้ทราบ
        builder.setTitle(strTitle);
        //ข้อความที่จะแจ้งเตืนอ
        builder.setMessage(strMessage);

        //dailog สูงสุด 3 ปุ่ม
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            //บางเครื่องก็เป็น dialoginterface ไม่ใช่ dialog แบบนี้ java แต่ละเวอร์ชั่นไม่เหมือนกัน แต่ใช้ได้เหมือนจากักับชาม
            public void onClick(DialogInterface dialog, int which) {
                //ถ้ากด ok ให้ popup หายไป
                dialog.dismiss();
            }
        });
        builder.show();

    }

}//Main Class
