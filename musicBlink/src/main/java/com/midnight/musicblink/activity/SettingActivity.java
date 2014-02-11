package com.midnight.musicblink.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.midnight.musicblink.R;

public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        final Button selectSoundButton = (Button) findViewById(R.id.sound_selector_button);

        selectSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onClickSoundSelector();
            }
        });


    }

    private void onClickSoundSelector() {
        final Intent intent = new Intent(this, ConfigureListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
}
