package com.midnight.musicblink.data;

import com.midnight.musicblink.data.impl.DBSoundData;

public class SoundDataManager {

    public static final SoundData getSoundData() {
        return new DBSoundData();
    }
}
