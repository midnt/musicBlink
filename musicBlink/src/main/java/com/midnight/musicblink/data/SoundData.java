package com.midnight.musicblink.data;

import com.midnight.musicblink.data.impl.SoundItem;

import java.util.List;

public interface SoundData {

    List<SoundItem> getAll();

    void addItem(SoundItem item);

    SoundItem getItem(int id);
}
