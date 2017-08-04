package xyz.zimuju.sample.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Calendar;

import cn.bmob.push.PushConstants;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.application.GankIOApplication;
import xyz.zimuju.sample.entity.bomb.PushMessage;
import xyz.zimuju.sample.surface.gank.WebViewActivity;

public class BombPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String msg = intent.getStringExtra("msg");
            Gson gson = new Gson();
            PushMessage message = gson.fromJson(msg, PushMessage.class);
            if (!TextUtils.isEmpty(message.getTitle()) &&
                    !TextUtils.isEmpty(message.getContent())) {
                showNotification(context, message);
            }
        }
    }

    private void showNotification(Context context, PushMessage message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getContent())
                        .setTicker(message.getContent());
        if (!TextUtils.isEmpty(message.getUrl())) {
            Intent resultIntent = new Intent(context, WebViewActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            resultIntent.putExtra(WebViewActivity.WEB_URL, message.getUrl());
            resultIntent.putExtra(WebViewActivity.TITLE, message.getTitle());
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(GankIOApplication.getInstance(), 0, resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }

        builder.setAutoCancel(true);

        int mNotificationId = Calendar.getInstance().get(Calendar.SECOND)
                + Calendar.getInstance().get(Calendar.MILLISECOND);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        NotificationManager notifyManager =
                (NotificationManager) GankIOApplication.getInstance()
                        .getSystemService(
                                Context.NOTIFICATION_SERVICE);
        notifyManager.notify(mNotificationId, notification);
    }
}
