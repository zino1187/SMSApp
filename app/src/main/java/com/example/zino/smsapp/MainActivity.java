package com.example.zino.smsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG=this.getClass().getName();

    SmsManager smsManager;
    String receiveNum="receiver phone number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsManager = SmsManager.getDefault();
    }

    public void send(View view){
        Log.d(TAG, "현재 이 앱의 SDK 버전은 "+Build.VERSION.SDK_INT);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            Log.d(TAG,"마시멜로 이하 버전입니다");
        }else{
            Log.d(TAG,"마시멜로 이상 버전입니다");
            if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED){
                String[] permissions={Manifest.permission.SEND_SMS};

                requestPermissions(permissions, 2);
            }else{
                sendSMS(receiveNum,"아이가 학교에 도착했어요");
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==2){
            if(permissions.length > 0 && grantResults[0]!=PackageManager.PERMISSION_DENIED){
                sendSMS(receiveNum,"아이가 학교에 도착했어요");
            }else{
                Toast.makeText(this,"SMS 권한을 부여해야 사용이 가능합니다",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void sendSMS(String dest, String text){
        smsManager.sendTextMessage(dest, null,text, null, null);
    }

}
