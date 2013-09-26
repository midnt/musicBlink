package com.midnight.musicblink.data;

import com.midnight.musicblink.data.impl.SoundItem;

import java.util.List;

public interface SoundData {

    public List<SoundItem> getAll();

    public void addItem(SoundItem item);

    public SoundItem getItem(int id);
}
