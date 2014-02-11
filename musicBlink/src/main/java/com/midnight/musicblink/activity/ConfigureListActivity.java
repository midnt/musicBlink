package com.midnight.musicblink.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.midnight.musicblink.R;
import com.midnight.musicblink.data.SoundData;
import com.midnight.musicblink.data.SoundDataManager;
import com.midnight.musicblink.data.impl.SoundItem;
import com.midnight.musicblink.provider.WidgetProvider;

public class ConfigureListActivity extends Activity {

    public static final int AUDIO_SELECTOR = 0x10;

    private EditText nameEditText;
    private SoundData soundData;
    private Uri soundFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurate_activity);

        soundData = SoundDataManager.getSoundData();

        nameEditText = (EditText) findViewById(R.id.dialog_name_text);
        Button selectSoundButton = (Button) findViewById(R.id.dialog_ok_button);
        selectSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                onClickOk();
            }
        });

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, AUDIO_SELECTOR);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AUDIO_SELECTOR: {
                if (resultCode == Activity.RESULT_OK && data.getData() != null) {
                    soundFileUri = data.getData();
                } else {
                    finish();
                }
                break;
            }
        }
    }

    private void onClickOk() {
        String name = nameEditText.getText().toString();
        SoundItem soundItem = new SoundItem();
        soundItem.setName(name);
        soundItem.setFileUri(((Object) soundFileUri).toString());
        soundData.addItem(soundItem);
        sendUpdateWidgetPendingIntent();
        finish();
    }

    private void sendUpdateWidgetPendingIntent() {

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName widgetComponent = new ComponentName(getApplicationContext(), WidgetProvider.class);
        int[] widgetIds = widgetManager.getAppWidgetIds(widgetComponent);

        for (int i : widgetIds) {
            widgetManager.notifyAppWidgetViewDataChanged(i, R.id.list_uri);
            Log.i("musicBlink", "send update " + i);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
