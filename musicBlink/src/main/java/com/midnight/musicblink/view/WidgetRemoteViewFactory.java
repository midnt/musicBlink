package com.midnight.musicblink.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.midnight.musicblink.R;
import com.midnight.musicblink.provider.WidgetProvider;

public class WidgetRemoteViewFactory {

    /**
     * Create remote view for widget.
     *
     * @param context context
     * @return remote view
     */
    public RemoteViews createView(final Context context, final Intent listAdapter) {
        final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.music_b_widget);
        final PendingIntent settingButton = createIntent(context, WidgetProvider.class, WidgetProvider.ACTION_SETTINGS);
        final PendingIntent stopButton = createIntent(context, WidgetProvider.class, WidgetProvider.ACTION_STOP);

        rv.setOnClickPendingIntent(R.id.button_setting, settingButton);
        rv.setOnClickPendingIntent(R.id.button_stop, stopButton);


        rv.setRemoteAdapter(R.id.list_uri, listAdapter);
        final Intent listClickIntent = new Intent(context, WidgetProvider.class);
        listClickIntent.setAction(WidgetProvider.ACTION_ON_CLICK);
        final PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0, listClickIntent, 0);
        rv.setPendingIntentTemplate(R.id.list_uri, listClickPIntent);

        return rv;
    }


    private PendingIntent createIntent(final Context context, final Class widgetClass, final String action) {
        final Intent intent = new Intent(context, widgetClass);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
