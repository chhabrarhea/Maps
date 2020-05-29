package com.example.maps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button) findViewById(R.id.btn_grant);
      if(  ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            Intent i=new Intent(MainActivity.this,map.class);
            startActivity(i);
            finish();
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(MainActivity.this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent i=new Intent(MainActivity.this,map.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if(response.isPermanentlyDenied())
                        {
                            AlertDialog ad=new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Permission denied permanently")
                                    .setMessage("Enable permission from app settings?")
                                    .setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i=new Intent();
                                            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            i.setData(Uri.fromParts("package", getPackageName(), null));


                                        }
                                    })
                                    .setNegativeButton("Cancel",null).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Permission Denied! :(", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).check();

            }
        });
    }
}
//AIzaSyDgUGd-RgYtiWI7uOvRz4vgFUrOAAOiSu8