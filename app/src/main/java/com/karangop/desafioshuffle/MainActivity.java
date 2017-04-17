package com.karangop.desafioshuffle;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PlayerService playerService;
    private ServiceConnection serviceConnection;
    private boolean isBound = false;

    private static final int RC_EXTERNAL = 343;


    private void bindToPlayer(){
        Intent intent = new Intent(this, PlayerService.class);
        serviceConnection = getServiceConnection();
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection getServiceConnection(){
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                isBound = true;
                PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
                playerService = binder.getService();
                playerService.setSongs();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound = false;

            }
        };
        return serviceConnection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            all permissions
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, RC_EXTERNAL);
        }else{
            bindToPlayer();
        }



        Button button = (Button) findViewById(R.id.playBtn);
        button.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        if (isBound){
            Log.d("Main activity","song name");
            playerService.getSongName();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (isBound){
            playerService.stopSongs();
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (isBound){
            playerService.playSongs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("REUEST CODE", String.valueOf(requestCode));
        Log.d("PERMISSIONS", permissions[0]);
        Log.d("RESULT", String.valueOf(grantResults[0]));
        if (RC_EXTERNAL == requestCode){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                bindToPlayer();
            }
        }
    }
}
