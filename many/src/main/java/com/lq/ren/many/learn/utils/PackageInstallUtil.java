package com.lq.ren.many.learn.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class PackageInstallUtil {
    private static final String TAG = "PackageInstallUtil";
    public static final String ACTION_INSTALL_COMPLETE = "com.mobvoi.appstore.INSTALL_COMPLETE";
    public static final String ACTION_UNINSTALL_COMPLETE = "com.mobvoi.appstore.UNINSTALL_COMPLETE";

    public static boolean installPackage(Context context, File apkFile, String packageName) {
        Log.d(TAG, "installPackage : %s", packageName);
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        // setup params
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setReferrerUri(Uri.fromFile(apkFile));
        params.setAppPackageName(packageName);

        OutputStream out = null;
        InputStream in = null;
        try {
            int sessionId = packageInstaller.createSession(params);
            // setup session
            PackageInstaller.Session session = packageInstaller.openSession(sessionId);
            out = session.openWrite(packageName, 0, -1);
            in = new FileInputStream(apkFile);
            byte[] buffer = new byte[65536];
            int c;
            while ((c = in.read(buffer)) != -1) {
                out.write(buffer, 0, c);
            }
            session.fsync(out);
//            IoUtils.closeQuietly(out);
//            IoUtils.closeQuietly(in);
            session.commit(createInstallIntentSender(context, sessionId, packageName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IoUtils.closeQuietly(out);
            IoUtils.closeQuietly(in);
        }
        return true;
    }

    public static void uninstallPackage(Context context, String pkgName) {
        PackageInfo packageInfo = ApkUtil.getPackageInfo(context, pkgName);
        if (packageInfo == null) {
            Log.e(TAG, "uninstallPackage fail, package not installed: " + pkgName);
            return;
        }
        Intent intent = new Intent(ACTION_UNINSTALL_COMPLETE);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        PackageInstaller mPackageInstaller = context.getPackageManager().getPackageInstaller();
        mPackageInstaller.uninstall(pkgName, sender.getIntentSender());
    }

    private static IntentSender createInstallIntentSender(Context context, int sessionId, String pkgName) {
        Intent intent = new Intent(ACTION_INSTALL_COMPLETE);
        intent.putExtra(PackageInstaller.EXTRA_PACKAGE_NAME, pkgName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, sessionId, intent, 0);
        return pendingIntent.getIntentSender();
    }
}
