package com.midnight.musicblink.service;

import android.content.Intent;
import android.widget.RemoteViewsService;
import com.midnight.musicblink.factory.ItemViewsFactory;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new ItemViewsFactory(getApplicationContext(), intent);
    }
}
