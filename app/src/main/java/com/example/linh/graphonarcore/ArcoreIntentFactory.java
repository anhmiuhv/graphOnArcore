package com.example.linh.graphonarcore;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.URI;

public class ArcoreIntentFactory {
    static public Intent getIntent(Activity activity, String url) {
        String packageName = "org.chromium.android_webview.shell";
        if (isAppInstalled(activity, packageName)){
            Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null){
                return intent.setData(Uri.parse(url));
            }
        }
        String idStr = url.substring(url.lastIndexOf('/') + 1);
        url = "https://argraph.herokuapp.com/nonar/" + idStr;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        return i;

    }

    private static boolean isAppInstalled(Activity activity, String packageName) {
        PackageManager pm = activity.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
