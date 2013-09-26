package com.midnight.musicblink.factory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.midnight.musicblink.R;
import com.midnight.musicblink.data.SoundData;
import com.midnight.musicblink.data.SoundDataManager;
import com.midnight.musicblink.data.impl.SoundItem;
import com.midnight.musicblink.provider.WidgetProvider;

import java.util.ArrayList;
import java.util.List;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
//    int widgetID;

    private List<SoundItem> data;
    private SoundData soundData;

    public WidgetFactory(final Context context, final Intent intent) {
        this.context = context;
        soundData = SoundDataManager.getSoundData();
        Log.i("musicB", "WidgetFactory constructor");

    }

    @Override
    public void onCreate() {
        data = new ArrayList<SoundItem>();
        Log.i("musicB", "factory_create");
    }

    @Override
    public void onDataSetChanged() {
        Log.i("musicB", "data change");
        data = soundData.getAll();
        Log.i("musicB", "onDataSetChanged");

        for (SoundItem item : data) {
            Log.i("musicB", "item: " + item.getName());
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_view);
        remoteViews.setTextViewText(R.id.music_name, data.get(position).getName());

        Intent clickIntent = new Intent();
        clickIntent.putExtra(WidgetProvider.ITEM_POSITION, position);
        remoteViews.setOnClickFillInIntent(R.id.music_name, clickIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
