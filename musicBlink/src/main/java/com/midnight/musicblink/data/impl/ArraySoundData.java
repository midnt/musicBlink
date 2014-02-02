package com.midnight.musicblink.data.impl;

import com.midnight.musicblink.data.SoundData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArraySoundData implements SoundData {

    private List<SoundItem> items;
    private static final ArraySoundData instance = new ArraySoundData();

    public static ArraySoundData getInstance() {
        return instance;
    }

    private ArraySoundData() {
        items = Collections.synchronizedList(new ArrayList<SoundItem>());
    }


    @Override
    public List<SoundItem> getAll() {
        return items.subList(0, items.size());
    }

    @Override
    public void addItem(final SoundItem item) {
        items.add(item);
    }

    @Override
    synchronized public SoundItem getItem(final int id) {
        return items.get(id);
    }
}
