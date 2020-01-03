package com.example.dropdown;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    Spinner s1;
    ArrayList<String> List = new ArrayList<String>();
    private CameraManager mCameraManager;
    private String mCameraId;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List.add("Choose");
        List.add("Gallery");
        List.add("Camera");
        List.add("Flashlight");
        List.add("Whatsapp");
        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);



        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        s1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this
                ,android.R.layout.simple_dropdown_item_1line,List);

        // Set the Adapter
        s1.setAdapter(arrayAdapter);
        s1.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                Log.w("Android",List.get(i));
                        String s= List.get(i);
                switch (s) {
                    case "Gallery": {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    }
                    case "Camera": {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivity(intent);

                        break;
                    }
                    case "Flashlight":
                        switchFlashLight(true);

                        break;
                    case "Whatsapp":

                        Intent waIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");

                        startActivity(Intent.createChooser(waIntent, "launch Whatsapp"));


                        break;
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
