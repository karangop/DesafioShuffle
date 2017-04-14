package com.karangop.desafioshuffle;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.karangop.desafioshuffle.models.Song;

import java.util.ArrayList;
import java.util.List;

public class PlayerService extends Service {

    private final IBinder binder = new LocalBinder();
    private List<Song> songs = new ArrayList<>();

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setSongs(){
        Uri tableName = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM};
        String clauses = MediaStore.Audio.Media.IS_MUSIC + " = ?";
        String[] arguments = {"1"};
        String sort = MediaStore.Audio.Media.ARTIST + "ASC";

        Cursor cursor = getContentResolver().query(tableName,projection,clauses,arguments,sort);
        if(cursor != null){
            Log.d("CURSOR", String.valueOf(cursor.getCount()));
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                Song song = new Song(cursor.getString(0), cursor.getString(2), cursor.getString(1), cursor.getString(3));
                songs.add(song);
                Log.d("SONGS", String.valueOf(songs.size()));
            }
        }
        else{
            Log.d("CURSOR","null");
        }


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
