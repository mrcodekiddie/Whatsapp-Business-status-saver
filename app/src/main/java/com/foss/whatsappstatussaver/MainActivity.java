package com.foss.whatsappstatussaver;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isFileReadPermissiongranted())
        {
            showPermissionRequestAlertmessage();
        }
        else
        {
            String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp Business/Media/.Statuses";
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            for (int i = 0; i < files.length; i++)
            {
                Log.d("Files", "FileName:" + files[i].getName());
                
            }
        }



    }
    private void showPermissionRequestAlertmessage()
    {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this)
                .setTitle("allow me please")
                .setMessage("Allow me to read and write files inorder to save status")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showPermissionRequestDialog();
                    }
                });
        alertDialog.show();
    }
    private boolean isFileReadPermissiongranted()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private void showPermissionRequestDialog()
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case  2:
                if((grantResults.length>0)&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Hurray, I got permission, Thanks", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Common.., I won't eat you.. Just allow me to write something to your files", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
