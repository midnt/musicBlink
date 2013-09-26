package com.midnight.musicblink.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import com.midnight.musicblink.R;
import com.midnight.musicblink.activity.ConfigureListActivity;
import com.midnight.musicblink.data.SoundDataManager;
import com.midnight.musicblink.mediaplayer.SoundPlayer;
import com.midnight.musicblink.service.WidgetService;

public class WidgetProvider extends AppWidgetProvider {

    private static final String SHOW_DIALOG_ACTION = "com.byteslounge.android.widgetshowdialog";
    private final static String ACTION_ON_CLICK = "com.byteslounge.android.widget.itemonclick";

    public final static String ITEM_POSITION = "item_position";


    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        Log.i("musicB", "onReceive");

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            Log.i("musicB", "receiver: " + action);

            if (SHOW_DIALOG_ACTION.equalsIgnoreCase(action)) {
                showConfigureActivity(context, intent.getIntExtra(ConfigureListActivity.EXTRA_WIDGET_ID, 0));
            } else if (ACTION_ON_CLICK.equals(action)) {
                onItemClick(context, intent.getIntExtra(ITEM_POSITION, -1));
            }
        }
//        if (intent != null && intent.getAction() != null) {
//            if (intent.getAction().equalsIgnoreCase(SHOW_DIALOG_ACTION)) {
//                Intent addDialogIntent = new Intent(context, AddDialog.class);
//                addDialogIntent.putExtra(AddDialog.EXTRA_WIDGET_ID, intent.getIntExtra(AddDialog.EXTRA_WIDGET_ID, 0));
//                addDialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(addDialogIntent);
//            } else if (intent.getAction().equalsIgnoreCase(ACTION_ON_CLICK)) {
//                int itemPos = intent.getIntExtra(ITEM_POSITION, -1);
//                if (itemPos != -1) {
//                    onItemClick(context, itemPos);
//                }
//            }
//        }


    }

    private void showConfigureActivity(final Context context, final int widgetId) {
        Intent intent = new Intent(context, ConfigureListActivity.class);
        intent.putExtra(ConfigureListActivity.EXTRA_WIDGET_ID, widgetId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void onItemClick(final Context context, final int position) {
        if (position < 0) {
            return;
        }

        Uri uri = Uri.parse(SoundDataManager.getSoundData().getItem(position).getFileUri());
        playUri(context, uri);
    }


    private void playUri(final Context context, final Uri uri) {
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(context, uri);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            Log.e("musicB", e.getMessage());
//        }
        SoundPlayer.getInstance().play(uri);

    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
    }

    private void updateWidget(final Context context, final AppWidgetManager manager, int widgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.music_b_widget);

        Log.i("musicB", "provider: update");

        setButtonListener(rv, context, widgetId, manager);

        setList(rv, context, widgetId);

        setListClickListener(rv, context, widgetId);


        manager.updateAppWidget(widgetId, rv);
        manager.notifyAppWidgetViewDataChanged(widgetId, R.id.music_list);


        Log.i("musicB", "updateWidget");

    }


    private void setList(final RemoteViews rv, final Context context, final int widgetId) {
        Intent adapter = new Intent(context, WidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        rv.setRemoteAdapter(R.id.music_list, adapter);
    }


    private void setButtonListener(final RemoteViews rv, final Context context, final int widgetId, final AppWidgetManager manager) {


        Intent intent = new Intent(context, WidgetProvider.class);
        intent.putExtra(ConfigureListActivity.EXTRA_WIDGET_ID, widgetId);
        intent.setAction(SHOW_DIALOG_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, intent, 0);
        rv.setOnClickPendingIntent(R.id.add_button, pendingIntent);
    }


    private void setListClickListener(RemoteViews rv, Context context, int appWidgetId) {
        Intent listClickIntent = new Intent(context, WidgetProvider.class);
        listClickIntent.setAction(ACTION_ON_CLICK);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0,
                listClickIntent, 0);
        rv.setPendingIntentTemplate(R.id.music_list, listClickPIntent);
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
