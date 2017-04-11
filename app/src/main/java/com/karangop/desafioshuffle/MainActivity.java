package com.karangop.desafioshuffle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PlayerService playerService;
    private ServiceConnection serviceConnection;
    private boolean isBound = false;

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
        bindToPlayer();

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
}
