package com.midnight.musicblink.factory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.midnight.musicblink.R;
import com.midnight.musicblink.data.SoundData;
import com.midnight.musicblink.data.impl.DBSoundData;
import com.midnight.musicblink.data.impl.SoundItem;
import com.midnight.musicblink.provider.WidgetProvider;

import java.util.List;


public class ItemViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<SoundItem> data;

    private Context context = null;
    private SoundData soundData = new DBSoundData();

    public ItemViewsFactory(final Context context, final Intent intent) {
        this.context = context;
        Log.i("musicBlink", "ItemViewsFactory " + intent.getAction());
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final SoundItem soundItem = data.get(position);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_view);

        remoteViews.setTextViewText(R.id.item_name, soundItem.getName());
        final Intent clickIntent = new Intent();
        clickIntent.putExtra(WidgetProvider.ITEM_URI, soundItem.getFileUri());
        remoteViews.setOnClickFillInIntent(R.id.item_name, clickIntent);

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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        Log.i("musicBlink", "onDataSetChanged");
        data = soundData.getAll();
    }
}
