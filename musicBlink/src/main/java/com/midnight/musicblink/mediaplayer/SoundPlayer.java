package com.midnight.musicblink.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundPlayer {

    private static final SoundPlayer instance = new SoundPlayer();
    private MediaPlayer mediaPlayer;
    private List<Uri> uriQueue;
    private Context context;

    private SoundPlayer() {
        mediaPlayer = new MediaPlayer();
        uriQueue = Collections.synchronizedList(new ArrayList<Uri>());
    }

    public static SoundPlayer getInstance() {
        return instance;
    }

    public void init(final Context context) {
        this.context = context;
    }

    synchronized public void play(final Uri uri) {
        final boolean isEmpty = uriQueue.isEmpty();
        uriQueue.add(uri);
        if (isEmpty) {
            try {
                playSound(uri);
            } catch (IOException e) {
                onSoundEnd(uri);
            }
        }

    }

    private void playSound(final Uri uri) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepare();
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                onSoundEnd(uri);
            }
        });
    }

    synchronized private void onSoundEnd(final Uri uri) {
        uriQueue.remove(uri);
        if (!uriQueue.isEmpty()) {
            final Uri nextUri = uriQueue.get(0);
            try {
                playSound(nextUri);
            } catch (IOException e) {
                onSoundEnd(nextUri);
            }
        }
    }


    public void stop() {
        uriQueue.clear();
        mediaPlayer.stop();
    }
}
