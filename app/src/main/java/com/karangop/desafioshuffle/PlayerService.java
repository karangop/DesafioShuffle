package com.karangop.desafioshuffle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service {

    private final IBinder binder = new LocalBinder();

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setSongs(){
        Log.d("PLAYERSERVICE","SET SONG");
    }

    public void playSongs(){
        Log.d("PLAYERSERVICE","PLAY THE SONG");
    }

    public void stopSongs(){
        Log.d("PLAYERSERVICE","STOPING THE SONG");
    }

    public String getSongName(){
        return "name the song";
    }

    public class LocalBinder extends Binder{

        public PlayerService getService(){
            return PlayerService.this;
        }
    }
}
