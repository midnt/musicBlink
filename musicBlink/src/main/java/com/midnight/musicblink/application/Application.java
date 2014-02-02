package com.midnight.musicblink.application;

import com.midnight.musicblink.data.DatabaseManager;
import com.midnight.musicblink.mediaplayer.SoundPlayer;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.getInstance().init(getApplicationContext());
        SoundPlayer.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
