package com.midnight.musicblink.data.impl;



public class SoundItem {

    private Integer id;
    private String name;
    private String fileUri;

    public SoundItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public SoundItem(final String name, final String fileUri) {
        this.name = name;
        this.fileUri = fileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(final String fileUri) {
        this.fileUri = fileUri;
    }

    @Override
    public String toString() {
        return getName();
    }
}
