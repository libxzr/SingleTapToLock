/*
 * Copyright (C) 2021 LibXZR <i@xzr.moe>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package moe.xzr.singletaptolock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.Timer;
import java.util.TimerTask;

public class WidgetProvider extends AppWidgetProvider {
    private Timer mTimer;
    private final int DELAY = 1000;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int id : appWidgetIds) {
            showBorder(id, context, appWidgetManager);
        }
        scheduleHideBorder(appWidgetIds, context, appWidgetManager);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        showBorder(appWidgetId, context, appWidgetManager);
        scheduleHideBorder(appWidgetId, context, appWidgetManager);
    }

    private void resetTimer(){
        if(mTimer!=null)
            mTimer.cancel();
        mTimer = new Timer();
    }

    private void scheduleHideBorder(int[] ids, Context context, AppWidgetManager appWidgetManager) {
        resetTimer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int id : ids) {
                    hideBorder(id, context, appWidgetManager);
                }
            }
        }, DELAY);
    }

    private void scheduleHideBorder(int id, Context context, AppWidgetManager appWidgetManager) {
        resetTimer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hideBorder(id, context, appWidgetManager);
            }
        }, DELAY);
    }

    private void showBorder(int id, Context context, AppWidgetManager appWidgetManager) {
        Intent intent = new Intent(context, WorkerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget_border);
        views.setOnClickPendingIntent(R.id.trigger_space, pendingIntent);
        appWidgetManager.updateAppWidget(id, views);
    }

    private void hideBorder(int id, Context context, AppWidgetManager appWidgetManager) {
        Intent intent = new Intent(context, WorkerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        views.setOnClickPendingIntent(R.id.trigger_space, pendingIntent);
        appWidgetManager.updateAppWidget(id, views);
    }
}
