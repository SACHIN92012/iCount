package com.example.android.icount;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanQRActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        cameraPreview=(SurfaceView)findViewById(R.id.camera_preview);
        createCameraSource();
    }
    private void createCameraSource()
    {
        BarcodeDetector barcodeDetector=new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource=new CameraSource.Builder(this,barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(1600,1024).build();
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(ScanQRActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestCameraPermission();
                    if(ActivityCompat.checkSelfPermission(ScanQRActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
                    {
                        try{
                            cameraSource.start(cameraPreview.getHolder());
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                else
                {
                    try{
                        cameraSource.start(cameraPreview.getHolder());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
                if(barcodes.size()>0)
                {

                    final TextView scanPreview=(TextView)findViewById(R.id.scan_preview);
                    final String s=String.valueOf(barcodes.valueAt(0).displayValue.toCharArray(),0,barcodes.valueAt(0).displayValue.length());
                    //scanPreview.setText(s);
                    Thread t=new Thread(){
                        @Override
                        public void run(){
                            try {


                                Thread.sleep(2000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        scanPreview.setText(s);
                                    }
                                });
                            }
                            catch (InterruptedException e)
                            {

                            }

                        }
                    };

                    Thread t1=new Thread(){
                        @Override
                        public void run(){
                            try {


                                Thread.sleep(4000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        scanPreview.setText("");
                                    }
                                });
                            }
                            catch (InterruptedException e)
                            {

                            }

                        }
                    };
                    t.start();
                    t1.start();

//                    Intent intent=new Intent();
//                    intent.putExtra("barcode",barcodes.valueAt(0));
//                    setResult(CommonStatusCodes.SUCCESS,intent);
                    //finish();
                }
            }
        });
    }

    private void requestCameraPermission() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission is required for Scanning QR Code")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ScanQRActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

}
