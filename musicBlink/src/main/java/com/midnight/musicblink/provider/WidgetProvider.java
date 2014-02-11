package com.midnight.musicblink.provider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.midnight.musicblink.activity.ConfigureListActivity;
import com.midnight.musicblink.mediaplayer.SoundPlayer;
import com.midnight.musicblink.service.WidgetService;
import com.midnight.musicblink.view.WidgetRemoteViewFactory;

public class WidgetProvider extends AppWidgetProvider {

    public static final String ACTION_SETTINGS = "com.midnight.musicblink.settings";
    public static final String ACTION_STOP = "com.midnight.musicblink.onitemstop";
    public final static String ACTION_ON_CLICK = "com.midnight.musicblink.onitemclick";
    public final static String ITEM_URI = "com.midnight.musicblink.itemuri";


    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        Log.i(getClass().getName(), action);
        if (ACTION_SETTINGS.equals(action)) {
            openSettings(context);
        } else if (ACTION_STOP.equals(action)) {
            SoundPlayer.getInstance().stop();
        } else if (ACTION_ON_CLICK.equals(action)) {
            final String uri = intent.getStringExtra(ITEM_URI);
            SoundPlayer.getInstance().play(createUri(uri));
        }
    }

    private void openSettings(final Context context) {
        final Intent intent = new Intent(context, ConfigureListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private Uri createUri(final String path) {
        return Uri.parse(path);
    }


    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final WidgetRemoteViewFactory remoteViewFactory = new WidgetRemoteViewFactory();
        final Intent adapter = new Intent(context, WidgetService.class);
        for (int i : appWidgetIds) {
            appWidgetManager.updateAppWidget(i, remoteViewFactory.createView(context, adapter));
        }
    }


    @Override
    public void onDeleted(final Context context, final int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(final Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(final Context context) {
        super.onDisabled(context);
    }
}
