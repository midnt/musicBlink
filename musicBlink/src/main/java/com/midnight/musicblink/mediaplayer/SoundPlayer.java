package com.midnight.musicblink.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundPlayer {

    private static final SoundPlayer instance = new SoundPlayer();
    private MediaPlayer mediaPlayer;
    private List<Uri> arrayList;
    private Context context;

    private SoundPlayer() {
        mediaPlayer = new MediaPlayer();
        arrayList = Collections.synchronizedList(new ArrayList<Uri>());
    }

    public static SoundPlayer getInstance() {
        return instance;
    }

    public void init(final Context context) {
        this.context = context;
    }


    synchronized public void play(final Uri uri) {
        if (arrayList.isEmpty()) {
            arrayList.add(uri);
            try {
                playSound(uri);
            } catch (IOException e) {
                onSoundEnd(uri);
            }
        } else {
            arrayList.add(uri);
        }

    }

    synchronized private void playSound(final Uri uri) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepare();
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                Log.i("musicB", "end");
                onSoundEnd(uri);
            }
        });
    }

    synchronized private void onSoundEnd(final Uri uri) {
        arrayList.remove(uri);
        if (!arrayList.isEmpty()) {
            Uri nextUri = arrayList.get(0);
            try {
                playSound(nextUri);
            } catch (IOException e) {
                onSoundEnd(nextUri);
            }

        }
    }


    public void stop() {
        arrayList.clear();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
}
