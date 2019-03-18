package com.foss.whatsappstatussaver;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    List<String> filePaths;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filePaths = new ArrayList<String>();
        if (!isFileReadPermissiongranted())
        {
            showPermissionRequestAlertmessage();
            readFiles();
            proceedNext();
        } else
        {
            readFiles();
            proceedNext();
        }


    }

    private void proceedNext()
    {
        recyclerView = findViewById(R.id.mRecyclerView);
        StatusViewAdapter adapter = new StatusViewAdapter(filePaths, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void readFiles()
    {
        String path = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/.Statuses";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();

        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            String filePath = files[i].getAbsolutePath();
            Log.d("Filesx", filePath);
            Log.d("FilesY", filePath.substring(filePath.length() - 4));
            Log.d("FilesZ", String.valueOf(files[i].lastModified()) + " : " + new Date(files[i].lastModified()).toString());
            filePaths.add(filePath);
        }
        filePaths=sortFiles(files);
    }

    private void showPermissionRequestAlertmessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("allow me please").setMessage("Allow me to read and write files inorder to save status").setPositiveButton("ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                showPermissionRequestDialog();
            }
        });
        alertDialog.show();
    }

    private boolean isFileReadPermissiongranted()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        } else
        {
            return true;
        }
    }

    private void showPermissionRequestDialog()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 2:
                if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Hurray, I got permission, Thanks", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(this, "Common.., I won't eat you.. Just allow me to write something to your files", Toast.LENGTH_SHORT).show();
                    showPermissionRequestDialog();
                }
        }
    }

    public List<String> sortFiles(File[] files)
    {
        List<String> tempFilesList=filePaths;

        for(int i=0;i<files.length;i++)
        {

            for(int j=0;j<files.length;j++)
            {
                if(files[i].lastModified()>files[j].lastModified())
                {
                    String path1=files[i].getAbsolutePath();
                    String path2=files[j].getAbsolutePath();
                    tempFilesList.set(i,path2);
                    tempFilesList.set(j,path1);
                }
            }
        }
        return tempFilesList;
    }


}
